package aslapov.android.study.pallada.kisuknd.raids.raidlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import aslapov.android.study.pallada.kisuknd.raids.data.UserRepository
import kotlinx.coroutines.launch

class RaidLisActivityViewModel(private val repository: UserRepository) : ViewModel() {

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