package aslapov.android.study.pallada.kisuknd.raids.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import aslapov.android.study.pallada.kisuknd.raids.data.UserRepository
import aslapov.android.study.pallada.kisuknd.raids.raidlist.RaidLisActivityViewModel

class AuthViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(RaidLisActivityViewModel::class.java)) {
            return RaidLisActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}