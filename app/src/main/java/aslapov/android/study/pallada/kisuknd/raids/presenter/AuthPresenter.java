package aslapov.android.study.pallada.kisuknd.raids.presenter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.auth.IAuthView;

public class AuthPresenter implements IBasePresenter<IAuthView> {

    private IAuthView mView;

    private String mUserName = null;

    @Override
    public void attachView(IAuthView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    public void tryAuth(@NonNull String userName, @NonNull String password) {
        if (TextUtils.isEmpty(userName)) {
            mView.showAuthError("Введите имя пользователя");
        } else {
            // TODO Auth here
            mUserName = userName;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
            raidRepo.login(userName, password, this);
        }
    }

    public void processLoginResponse(JsonObject loginResponseJson) {
        JsonElement jsonElement = loginResponseJson.get("StatusCode");
        if (jsonElement != null) {
            switch (jsonElement.getAsInt()) {
                case 100:   //  Правильный логин
                    mView.showPasswordLayout();
                    break;
                case 403:   // Ошибка логина или пароля
                    mView.clearView();
                    mView.showAuthError("Не удалось выполнить вход, проверьте правильность введенных данных!");
                    break;
                case 204:   // Пользователь найден в системе КИСУ. Регистрация
                    mView.showPasswordLayout();
                    mView.showAuthInfo("Придумайте новый пароль для регистрации в системе.");
                    break;
                default:
                    mView.showAuthError("Ошибка сервера!");
                    break;
            }
            return;
        }
        if (loginResponseJson.get("Succeeded") != null && loginResponseJson.get("Succeeded").getAsBoolean()) {
            mView.showAuthInfo("Пользователь зарегистрирован. Войдите в систему");
            return;
        }
        // Успешная авторизация
        if (loginResponseJson.get("UserName").getAsString().equals(mUserName.toUpperCase())) {
            mView.openRaidListScreen();
        }
    }

    public void showError(String message) {
        mView.showAuthError(message);
    }
}
