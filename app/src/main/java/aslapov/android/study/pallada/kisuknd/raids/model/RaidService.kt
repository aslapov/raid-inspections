package aslapov.android.study.pallada.kisuknd.raids.model

import aslapov.android.study.pallada.kisuknd.raids.auth.TicketEntry
import aslapov.android.study.pallada.kisuknd.raids.auth.LoggedInUser
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

interface RaidService {
    @POST("proxy/auth")
    fun login(@Body user: LoggedInUser): Observable<TicketEntry>
}