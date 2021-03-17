package aslapov.android.study.pallada.kisuknd.raids.auth

import aslapov.android.study.pallada.kisuknd.raids.model.AuthApiService
import aslapov.android.study.pallada.kisuknd.raids.model.ApiFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException

class AuthRepository(private val dataSource: AuthDataSource) {
    enum class AuthApiResult {
        SUCCESS, UNAUTHORIZED, ERROR
    }

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = dataSource.getUser()
    }

    fun login(user: LoggedInUser) {
        val subscriberResult = service.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            val ticketId = response.ticket.id;
                            ApiFactory.setAuthTicket(ticketId)
                            dataSource.setUser(user)
                            _authResult.value = AuthApiResult.SUCCESS
                        },
                        { error ->
                            val exception = error as HttpException
                            _authResult.value = if (exception.code() == 403)
                                AuthApiResult.UNAUTHORIZED
                            else
                                AuthApiResult.ERROR
                        }
                )
    }

    fun isAuthentificated(): Observable<TicketEntry> {
        return service.isAuthentificated();
    }

    fun getSessionStatus() {

    }

    fun logout() {
        user = null
        dataSource.logout()
        service.logout()
    }

    private val service: AuthApiService = ApiFactory.getAuthService()

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}