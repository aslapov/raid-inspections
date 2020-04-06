package aslapov.android.study.pallada.kisuknd.raids.model;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.content.LoggedInUser;
import aslapov.android.study.pallada.kisuknd.raids.model.content.Raid;
import aslapov.android.study.pallada.kisuknd.raids.presenter.AuthPresenter;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidListPresenter;
import aslapov.android.study.pallada.kisuknd.raids.presenter.RaidPresenter;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaidRepository {

    public void login(String username, String password, AuthPresenter presenter) {
        LoggedInUser user = new LoggedInUser(username, password);
        RaidApiFactory.getRaidService().login(user).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonResponseString = response.body().string();
                        presenter.processLoginResponse(new Gson().fromJson(jsonResponseString, JsonObject.class));
                    } catch (IOException e) {
                        e.printStackTrace();
                        presenter.showError(e.getMessage());
                    }
                    int s = response.code();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String s = "";
            }
        });

    }

    public List<Raid> queryRaids(RaidListPresenter presenter) {
        RaidApiFactory.getRaidService().queryRaids().enqueue(new Callback<List<Raid>>() {
            @Override
            public void onResponse(Call<List<Raid>> call, Response<List<Raid>> response) {
                if (response.isSuccessful()) {
                    List<Raid> raids = response.body();
                    presenter.setRaids(raids);
                    presenter.showRaids();
                }
            }

            @Override
            public void onFailure(Call<List<Raid>> call, Throwable t) {

            }
        });

        return null;
    }

    public Raid queryRaidById(UUID raidId, RaidPresenter presenter) {
        RaidApiFactory.getRaidService().queryRaidById(raidId).enqueue(new Callback<Raid>() {
            @Override
            public void onResponse(Call<Raid> call, retrofit2.Response<Raid> response) {
                // код 200
                if (response.isSuccessful()) {
                    Raid raid = response.body();
                    presenter.setRaid(raid);
                    presenter.showRaidInfo(raid);
                } else {
                    switch (response.code()) {
                        case 401:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Raid> call, Throwable t) {

            }
        });

        return null;
    }
}
