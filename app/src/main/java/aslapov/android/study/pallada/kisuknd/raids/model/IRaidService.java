package aslapov.android.study.pallada.kisuknd.raids.model;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.transfer.AuthResponse;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.LoggedInUser;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.Raid;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRaidService {
    @GET("IsAuthentificated")
    public Call<ResponseBody> isAuthentificated();

    @POST("Login")
    public Call<AuthResponse> login(@Body LoggedInUser loggedInUser);

    @GET("Inspections")
    public Call<List<Raid>> queryRaids();

    @GET("Inspections/{id}")
    public Call<Raid> queryRaidById(@Path("id") UUID raidId);
}
