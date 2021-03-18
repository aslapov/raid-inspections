package aslapov.android.study.pallada.kisuknd.raids.model

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class AuthDataSource(private val applicationContext: Context) {

    fun getUser(): LoggedInUser? {
        val username = sharedPreferences.getString(USERNAME_KEY, null)
        val password = sharedPreferences.getString(PASSWORD_KEY, null)

        return if (username != null)
            LoggedInUser(username, password!!)
        else
            null
    }

    fun setUser(user: LoggedInUser) {
        with (sharedPreferences.edit()) {
            putString(USERNAME_KEY, user.username)
            putString(PASSWORD_KEY, user.password)
            apply()
        }
    }

    fun logout() {
        with (sharedPreferences.edit()) {
            putString(USERNAME_KEY, null)
            putString(PASSWORD_KEY, null)
            apply()
        }
    }

    private val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
            applicationContext,
            USER_SHARED_PREFS_FILE,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private const val USER_SHARED_PREFS_FILE: String = "USER_SHARED_PREFS_FILE"
        private const val USERNAME_KEY: String = "LOGIN_KEY"
        private const val PASSWORD_KEY: String = "PASSWORD_KEY"
    }
}