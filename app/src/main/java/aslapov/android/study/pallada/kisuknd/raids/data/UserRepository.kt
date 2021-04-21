package aslapov.android.study.pallada.kisuknd.raids.data

import aslapov.android.study.pallada.kisuknd.raids.AuthResult
import aslapov.android.study.pallada.kisuknd.raids.data.source.local.UserDataSource
import aslapov.android.study.pallada.kisuknd.raids.data.source.remote.ApiFactory
import aslapov.android.study.pallada.kisuknd.raids.data.source.remote.AuthApiService
import aslapov.android.study.pallada.kisuknd.raids.data.source.remote.Person
import aslapov.android.study.pallada.kisuknd.raids.data.source.remote.UserCredentials
import aslapov.android.study.pallada.kisuknd.raids.user.User
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException

class UserRepository(
        private val userDataSource: UserDataSource,
        private val authService: AuthApiService
) {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = userDataSource.getUser()
    }

    suspend fun login(username: String, password: String): AuthResult = try {
            val userCredentials = UserCredentials(username, password)
            val ticketEntry = authService.login(userCredentials)
            ApiFactory.setAuthTicket(ticketEntry.ticket.id)

            val person = authService.getPerson()
            val sites = authService.getPersonSites()
            val userDepartments = sites.list.entries
                    .map { x -> x.entry.site }
                    .filter { site -> site.visibility == "PRIVATE" }
                    .map { site -> site.title }
            setLoggedInUser(person.entry, userDepartments)

            AuthResult.AUTHORIZED
        } catch (e: ConnectException) {
            AuthResult.ERROR
        } catch (e: HttpException) {
            if (e.code() == 403)
                AuthResult.UNAUTHORIZED
            else
                AuthResult.ERROR
        } catch (e: Exception) {
            AuthResult.ERROR
        }

    suspend fun logout(): Boolean = try {
            authService.logout()
            userDataSource.logout()
            user = null
            true
        } catch (error: Exception) {
            false
        }

    private fun setLoggedInUser(person: Person) {
        setLoggedInUser(person, null)
    }

    private fun setLoggedInUser(person: Person, userDepartments: List<String>?) {
        this.user = User(
                person.id,
                person.firstName,
                person.lastName,
                person.displayName,
                person.email,
                person.jobTitle,
                userDepartments
        )
        user?.let { userDataSource.setUser(it) }
    }
}