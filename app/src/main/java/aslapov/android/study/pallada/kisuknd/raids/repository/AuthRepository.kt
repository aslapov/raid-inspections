package aslapov.android.study.pallada.kisuknd.raids.repository

import androidx.lifecycle.MediatorLiveData
import aslapov.android.study.pallada.kisuknd.raids.model.AuthDataSource
import aslapov.android.study.pallada.kisuknd.raids.model.AuthApiService
import aslapov.android.study.pallada.kisuknd.raids.model.ApiFactory
import aslapov.android.study.pallada.kisuknd.raids.model.AuthResult
import aslapov.android.study.pallada.kisuknd.raids.model.LoggedInUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.net.ConnectException

class AuthRepository(private val dataSource: AuthDataSource) {

    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        user = dataSource.getUser()
    }

    private val service: AuthApiService = ApiFactory.getAuthService()

    private val result = MediatorLiveData<AuthResult>()

    fun login(user: LoggedInUser){
        val subscriberResult = service.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { response ->
                            val ticketId = response.ticket.id;
                            ApiFactory.setAuthTicket(ticketId)
                            setLoggedInUser(user)
                            dataSource.setUser(user)
                            result.value = AuthResult.AUTHORIZED
                        },
                        { error ->
                            when (error) {
                                is ConnectException -> result.value = AuthResult.ERROR
                                is HttpException -> {
                                    result.value = if (error.code() == 403)
                                        AuthResult.UNAUTHORIZED
                                    else
                                        AuthResult.ERROR
                                }
                                else -> result.value = AuthResult.ERROR
                            }
                        }
                )
    }

    fun logout() {
        user = null
        dataSource.logout()
        service.logout()
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
    }
}