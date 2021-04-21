package aslapov.android.study.pallada.kisuknd.raids.auth

import androidx.lifecycle.*
import aslapov.android.study.pallada.kisuknd.raids.R
import aslapov.android.study.pallada.kisuknd.raids.AuthFormState
import aslapov.android.study.pallada.kisuknd.raids.data.UserRepository
import aslapov.android.study.pallada.kisuknd.raids.AuthResult
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: UserRepository) : ViewModel() {

    private val _authForm = MutableLiveData<AuthFormState>()
    val authFormState: LiveData<AuthFormState> = _authForm

    private val _authResult = MutableLiveData<AuthResult>()
    val authResult: LiveData<AuthResult> = _authResult

    init {
        if (repository.isLoggedIn)
            _authResult.value = AuthResult.AUTHORIZED
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _authResult.value = repository.login(username, password)
        }
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