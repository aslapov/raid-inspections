package aslapov.android.study.pallada.kisuknd.raids.view.auth;

import aslapov.android.study.pallada.kisuknd.raids.view.ILoadingView;

public interface IAuthView extends ILoadingView {
    // Открыть представление со списком рейдовых осмотров ТС
    void openRaidListScreen();

    // Вывести информацию
    void showAuthInfo(String message);

    // Вывести сообщение об ошибке авторизации
    void showAuthError(String message);

    // Вывести поле для заполнения пароля
    void showPasswordLayout();

    // Очистить представление
    void clearView();
}
