package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.AuthResponse;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.LoggedInUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaidRepository {

    public interface ResponseCallback<T> {
        void onResponse(T result);
        void onError(Throwable t);
    }

    private RaidDao mRaidDao;
    private LiveData<List<RaidWithInspectors>> mRaids;

    public RaidRepository(Context applicationContext) {
        RaidRoomDatabase db = RaidRoomDatabase.getDatabase(applicationContext);
        mRaidDao = db.raidDao();
    }

    public LiveData<List<RaidWithInspectors>> queryRaids(boolean isDraft) {
        return mRaidDao.queryRaidList(isDraft);
    }

    public LiveData<RaidWithInspectors> queryRaidListById(UUID raidId) {
        return mRaidDao.queryRaidListById(raidId.toString());
    }

    public void updateRaid(RaidWithInspectors raid) {
        mRaidDao.updateRaidWithInspectors(raid.getRaid(), raid.getInspectors());
    }

    public void addRaid(RaidWithInspectors raid) {
        mRaidDao.insertRaidWithInspectors(raid.getRaid(), raid.getInspectors());
    }

    public void login(String username, String password, ResponseCallback<AuthResult> callback) {
        LoggedInUser user = new LoggedInUser(username.toUpperCase(), password);
        RaidApiFactory.getRaidService().login(user).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResult result = new AuthResult();

                if (response.isSuccessful()) {
                    AuthResponse authResponse = response.body();

                    if (authResponse.getStatusCode() != null) {
                        switch (authResponse.getStatusCode()) {
                            case 100:   //  Правильный логин
                                result.setAuthState(AuthResult.State.LOGINED);
                                break;
                            case 403:   // Ошибка логина или пароля
                                result.setAuthState(AuthResult.State.WRONG);
                                break;
                            case 204:   // Пользователь найден в системе КИСУ. Регистрация
                                result.setAuthState(AuthResult.State.REGISTRATION);
                                break;
                            default:
                                result.setAuthState(AuthResult.State.FAIL);
                                break;
                        }
                        callback.onResponse(result);
                        return;
                    }
                    if (authResponse.isSucceeded()) {
                        result.setAuthState(AuthResult.State.REGISTERED);
                        callback.onResponse(result);
                        return;
                    }
                    // Успешная авторизация
                    if (username.toUpperCase().equals(authResponse.getUserName())) {
                        result.setAuthState(AuthResult.State.SUCCESS);
                        callback.onResponse(result);
                    }
                } else {
                    result.setAuthState(AuthResult.State.FAIL);
                    callback.onResponse(result);
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                callback.onError(t);
            }
        });
    }

    /*public void queryRaids(ResponseCallback<List<Raid>> callback) {
        RaidApiFactory.getRaidService().queryRaids().enqueue(new Callback<List<Raid>>() {
            @Override
            public void onResponse(Call<List<Raid>> call, Response<List<Raid>> response) {
                if (response.isSuccessful())

                    /*for (Raid raid : response.body()) {
                        Raid raidEntity = new Raid();
                        raidEntity.setId(raid.getId().toString());
                        raidEntity.setActNumber(raid.getActNumber());
                        raidEntity.setDepartment(raid.getDepartment());
                        raidEntity.setTransportType(raid.getTransportType());
                        raidEntity.setOrderDate(raid.getOrderDate());
                        raidEntity.setOrderNumber(raid.getOrderNumber());
                        raidEntity.setOwnerInn(raid.getOwnerInn());
                        raidEntity.setOwnerOgrn(raid.getOwnerOgrn());
                        raidEntity.setPlaceAddress(raid.getPlaceAddress());
                        raidEntity.setRealEnd(raid.getRealEnd());
                        raidEntity.setRealStart(raid.getRealStart());
                        raidEntity.setTaskDate(raid.getTaskDate());
                        raidEntity.setTaskNumber(raid.getTaskNumber());

                        List<RaidInspectionMember> inspectors = new ArrayList<>();
                        for (String inspector : raid.getInspectors()) {
                            RaidInspectionMember member = new RaidInspectionMember();
                            member.setContactName(inspector);
                            member.setRaidInspectionId(raid.getId().toString());
                            inspectors.add(member);
                        }
                        mRaidDao.insertRaidWithInspectors(raidEntity, inspectors);
                    }
                    response.body();
                    List<RaidWithInspectors> raids = mRaidDao.queryRaidList();
                    callback.onResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Raid>> call, Throwable t) {
                callback.onError(t);
            }
        });
    }*/

    /*public void queryRaidById(UUID raidId, ResponseCallback<Raid> callback) {
        RaidApiFactory.getRaidService().queryRaidById(raidId).enqueue(new Callback<Raid>() {

            @Override
            public void onResponse(Call<Raid> call, retrofit2.Response<Raid> response) {
                // код 200
                if (response.isSuccessful()) {
                    callback.onResponse(response.body());
                } else {
                    switch (response.code()) {
                        case 401:
                            break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Raid> call, Throwable t) {
                callback.onError(t);
            }
        });
    }*/
}
