package aslapov.android.study.pallada.kisuknd.raids.model

import aslapov.android.study.pallada.kisuknd.raids.model.transfer.TicketEntry
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("proxy/auth")
    fun login(@Body loggedInUser: LoggedInUser): Observable<TicketEntry>

    @GET("proxy/auth/ticket")
    fun isAuthentificated(): Observable<TicketEntry>

    @DELETE("proxy/auth/ticket")
    fun logout()
}