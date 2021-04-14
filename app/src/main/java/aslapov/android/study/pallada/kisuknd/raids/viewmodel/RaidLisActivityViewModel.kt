package aslapov.android.study.pallada.kisuknd.raids.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aslapov.android.study.pallada.kisuknd.raids.repository.AuthRepository
import kotlinx.coroutines.launch

class RaidLisActivityViewModel(private val repository: AuthRepository) : ViewModel() {

    private val _logoutResult = MutableLiveData<Boolean>()
    val logoutResult: LiveData<Boolean> = _logoutResult

    fun getUserDisplayName() = repository.user?.displayName

    fun getUserDepartments() = repository.user?.departments

    fun logout() {
        viewModelScope.launch {
            _logoutResult.value = repository.logout()
        }
    }
}