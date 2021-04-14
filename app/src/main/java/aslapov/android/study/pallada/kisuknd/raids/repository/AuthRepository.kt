package aslapov.android.study.pallada.kisuknd.raids.repository

import aslapov.android.study.pallada.kisuknd.raids.model.*
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Person
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Site
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.UserCredentials
import retrofit2.HttpException
import java.lang.Exception
import java.net.ConnectException

class AuthRepository(
        private val dataSource: AuthDataSource,
        private val authService: AuthApiService
) {
    var user: User? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = dataSource.getUser()
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
            dataSource.logout()
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
        user?.let { dataSource.setUser(it) }
    }
}