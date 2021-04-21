package aslapov.android.study.pallada.kisuknd.raids

import android.app.Application
import aslapov.android.study.pallada.kisuknd.raids.data.source.remote.ApiFactory
import aslapov.android.study.pallada.kisuknd.raids.data.source.local.UserDataSource
import aslapov.android.study.pallada.kisuknd.raids.data.source.local.RaidRoomDatabase
import aslapov.android.study.pallada.kisuknd.raids.data.RepositoryProvider
import aslapov.android.study.pallada.kisuknd.raids.data.UserRepository

class RaidApplication : Application() {
    val database by lazy { RaidRoomDatabase.getDatabase(this) }

    val authService by lazy { ApiFactory.getAuthService() }
    val raidService by lazy { ApiFactory.getRaidService() }

    val authDataSource by lazy { UserDataSource(this) }

    val authRepository by lazy { UserRepository(authDataSource, authService) }
    val raidRepository by lazy { RepositoryProvider.provideRaidRepository(this) }
}