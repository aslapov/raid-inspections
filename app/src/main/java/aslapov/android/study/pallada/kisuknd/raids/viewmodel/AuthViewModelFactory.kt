package aslapov.android.study.pallada.kisuknd.raids.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import aslapov.android.study.pallada.kisuknd.raids.repository.AuthRepository
import aslapov.android.study.pallada.kisuknd.raids.ui.auth.AuthViewModel

class AuthViewModelFactory(private val repository: AuthRepository) : ViewModelProvider.Factory {
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