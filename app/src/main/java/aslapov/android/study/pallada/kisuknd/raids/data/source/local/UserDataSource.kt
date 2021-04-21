package aslapov.android.study.pallada.kisuknd.raids.data.source.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import aslapov.android.study.pallada.kisuknd.raids.user.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserDataSource(private val applicationContext: Context) {

    fun getUser(): User? {
        val id = sharedPreferences.getString(ID_KEY, null)
        val firstName = sharedPreferences.getString(FIRSTNAME_KEY, null)
        val lastName = sharedPreferences.getString(LASTNAME_KEY, null)
        val displayName = sharedPreferences.getString(DISPLAYNAME_KEY, null)
        val email = sharedPreferences.getString(EMAIL_KEY, null)
        val jobTitle = sharedPreferences.getString(JOBTITLE_KEY, null)

        val departmentsJson = sharedPreferences.getString(DEPARTMENT_KEY, null)
        val departmentsType = object : TypeToken<List<String>?>(){}.type
        val departments = Gson().fromJson<List<String>?>(departmentsJson, departmentsType)

        return if (firstName != null)
            User(id, firstName, lastName, displayName, email, jobTitle, departments)
        else
            null
    }

    fun setUser(user: User) {
        with (sharedPreferences.edit()) {
            putString(ID_KEY, user.id)
            putString(FIRSTNAME_KEY, user.firstName)
            putString(LASTNAME_KEY, user.lastName)
            putString(DISPLAYNAME_KEY, user.displayName)
            putString(EMAIL_KEY, user.email)
            putString(JOBTITLE_KEY, user.jobTitle)

            val departmentsJson = Gson().toJson(user.departments)
            putString(DEPARTMENT_KEY, departmentsJson)
            apply()
        }
    }

    fun logout() {
        with (sharedPreferences.edit()) {
            putString(ID_KEY, null)
            putString(FIRSTNAME_KEY, null)
            putString(LASTNAME_KEY, null)
            putString(DISPLAYNAME_KEY, null)
            putString(EMAIL_KEY, null)
            putString(JOBTITLE_KEY, null)
            putString(DEPARTMENT_KEY, null)
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
        private const val ID_KEY: String = "ID_KEY"
        private const val FIRSTNAME_KEY: String = "FIRSTNAME_KEY"
        private const val LASTNAME_KEY: String = "LASTNAME_KEY"
        private const val DISPLAYNAME_KEY: String = "DISPLAYNAME_KEY"
        private const val EMAIL_KEY: String = "EMAIL_KEY"
        private const val JOBTITLE_KEY: String = "JOBTITLE_KEY"
        private const val DEPARTMENT_KEY: String = "DEPARTMENT_KEY"
    }
}