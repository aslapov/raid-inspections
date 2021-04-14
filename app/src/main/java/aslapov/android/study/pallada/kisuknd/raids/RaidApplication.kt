package aslapov.android.study.pallada.kisuknd.raids

import android.app.Application
import aslapov.android.study.pallada.kisuknd.raids.model.ApiFactory
import aslapov.android.study.pallada.kisuknd.raids.model.AuthDataSource
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRoomDatabase
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider
import aslapov.android.study.pallada.kisuknd.raids.repository.AuthRepository

class RaidApplication : Application() {
    val database by lazy { RaidRoomDatabase.getDatabase(this) }

    val authService by lazy { ApiFactory.getAuthService() }
    val raidService by lazy { ApiFactory.getRaidService() }

    val authDataSource by lazy { AuthDataSource(this) }

    val authRepository by lazy { AuthRepository(authDataSource, authService) }
    val raidRepository by lazy { RepositoryProvider.provideRaidRepository(this) }
}