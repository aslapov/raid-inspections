package aslapov.android.study.pallada.kisuknd.raids.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import aslapov.android.study.pallada.kisuknd.raids.R
import aslapov.android.study.pallada.kisuknd.raids.model.ApiFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class AuthViewModel(private val repository: AuthRepository) : ViewModel() {
    enum class AuthResult {
        SUCCESS, UNAUTHORIZED, ERROR
    }

    private val _authForm = MutableLiveData<AuthFormState>()
    val authFormState: LiveData<AuthFormState> = _authForm

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    fun login(username: String, password: String) {
        val user = LoggedInUser(username, password)
        repository.login(user)
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _authForm.value = AuthFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _authForm.value = AuthFormState(passwordError = R.string.invalid_password)
        } else {
            _authForm.value = AuthFormState(isDataValid = true)
        }
    }

    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.isNotBlank()
    }
}