package aslapov.android.study.pallada.kisuknd.raids.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import aslapov.android.study.pallada.kisuknd.raids.model.IRaidService
import io.reactivex.Observable

class AuthRepository(private val applicationContext: Context, private val service: IRaidService) {
    enum class SessionStatus {
        VALID, EXPIRED, DISCONNECTED, UNEXPECTED
    }

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = tryGetUser()
    }

    fun login(user: LoggedInUser): Observable<TicketEntry> {
        return service.login(user)
    }

    fun isAuthentificated(): Observable<TicketEntry> {
        return service.isAuthentificated();
    }

    fun getSessionStatus() {

    }

    fun logout() {
        user = null
        service.logout()
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }

    private fun tryGetUser(): LoggedInUser? {
        val mainKey = MasterKey.Builder(applicationContext)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
                applicationContext,
                USER_SHARED_PREFS_FILE,
                mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val username = sharedPreferences.getString(LOGIN_KEY, null)
        val password = sharedPreferences.getString(PASSWORD_KEY, null)

        return if (username != null)
                LoggedInUser(username, password!!)
            else
                null
    }

    companion object {
        private const val USER_SHARED_PREFS_FILE: String = "USER_SHARED_PREFS_FILE"
        private const val LOGIN_KEY: String = "LOGIN_KEY"
        private const val PASSWORD_KEY: String = "PASSWORD_KEY"
    }
}