package aslapov.android.study.pallada.kisuknd.raids.presenter;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;
import aslapov.android.study.pallada.kisuknd.raids.view.auth.IAuthView;

public class AuthPresenter {

    private final LifecycleOwner mLifecycleOwner;
    private final IAuthView mAuthView;

    private String mUserName = null;

    public AuthPresenter(@NonNull LifecycleOwner lifecycleOwner,
                         @NonNull IAuthView authView) {
        mLifecycleOwner = lifecycleOwner;
        mAuthView = authView;
    }

    public void tryAuth(@NonNull String userName, @NonNull String password) {
        if (TextUtils.isEmpty(userName)) {
            mAuthView.showAuthError("Введите имя пользователя");
        } else {
            // TODO Auth here
            mUserName = userName;
            RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
            raidRepo.login(userName, password, this);
            //mAuthView.openRaidListScreen();
        }
    }

    public void processLoginResponse(JsonObject loginResponseJson) {
        JsonElement jsonElement = loginResponseJson.get("StatusCode");
        if (jsonElement != null) {
            switch (jsonElement.getAsInt()) {
                case 100:   //  Правильный логин
                    mAuthView.showPasswordLayout();
                    break;
                case 403:   // Ошибка логина или пароля
                    mAuthView.tryAgain();
                    break;
                case 204:   // Пользователь найден в системе КИСУ. Регистрация
                    mAuthView.showPasswordLayout();
                    mAuthView.showAuthError("Придумайте новый пароль для регистрации в системе.");
                    break;
                default:
                    mAuthView.showAuthError("Ошибка сервера!");
                    break;
            }
            return;
        }
        if (loginResponseJson.get("Succeeded") != null && loginResponseJson.get("Succeeded").getAsBoolean()) {
            mAuthView.showAuthError("Пользователь зарегистрирован. Войдите в систему");
            return;
        }
        // Успешная авторизация
        if (loginResponseJson.get("UserName").getAsString().equals(mUserName.toUpperCase())) {
            mAuthView.openRaidListScreen();
        }
    }

    public void showError(String message) {

    }
}
