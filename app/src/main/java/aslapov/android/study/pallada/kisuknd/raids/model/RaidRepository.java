package aslapov.android.study.pallada.kisuknd.raids.model;

import android.content.Context;

import java.util.List;
import java.util.UUID;

import aslapov.android.study.pallada.kisuknd.raids.model.local.RaidWithInspectors;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.AuthResponse;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.LoggedInUser;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RaidRepository {

    public interface ResponseCallback<T> {
        void onResponse(T result);
        void onError(Throwable t);
    }

    private RaidDao mRaidDao;

    RaidRepository(Context applicationContext) {
        RaidRoomDatabase db = RaidRoomDatabase.getDatabase(applicationContext);
        mRaidDao = db.raidDao();
    }

    public Observable<List<RaidWithInspectors>> queryRaids(boolean isDraft) {
        return mRaidDao.queryRaidList(isDraft);
    }

    public Observable<RaidWithInspectors> queryRaidById(UUID raidId) {
        return mRaidDao.queryRaidById(raidId.toString());
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

}
