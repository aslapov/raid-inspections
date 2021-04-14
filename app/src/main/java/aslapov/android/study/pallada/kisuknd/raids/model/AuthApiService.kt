package aslapov.android.study.pallada.kisuknd.raids.model

import aslapov.android.study.pallada.kisuknd.raids.model.transfer.PersonEntry
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.SiteRolePaging
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.TicketEntry
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.UserCredentials
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {
    @POST("proxy/auth")
    suspend fun login(@Body loggedInUser: UserCredentials): TicketEntry

    @GET("proxy/auth/ticket")
    suspend fun isAuthentificated(): TicketEntry

    @GET("proxy/api/-default-/public/alfresco/versions/1/people/-me-")
    suspend fun getPerson(): PersonEntry

    @GET("proxy/api/-default-/public/alfresco/versions/1/people/-me-/sites")
    suspend fun getPersonSites(): SiteRolePaging

    @DELETE("proxy/auth/ticket")
    suspend fun logout()
}