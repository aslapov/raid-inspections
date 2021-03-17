package aslapov.android.study.pallada.kisuknd.raids.model;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.auth.TicketEntry;
import aslapov.android.study.pallada.kisuknd.raids.auth.LoggedInUser;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Entry;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Raid;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RaidApiService {

    @GET("Inspections")
    Observable<List<Raid>> queryRaids();

    /*@GET("Inspections/{id}")
    Observable<Raid> queryRaidById(@Path("id") UUID raidId);*/

    @GET("alfresco/api/-default-/public/alfresco/versions/1/nodes/{id}")
    Call<Entry> queryRaidById(@Path("id") UUID raidId);
}
