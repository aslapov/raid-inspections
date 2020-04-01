package aslapov.android.study.pallada.kisuknd.raids.view.auth;

import aslapov.android.study.pallada.kisuknd.raids.view.ILoadingView;

public interface IAuthView extends ILoadingView {

    void openRaidListScreen();

    void showAuthError(String message);

    void showPasswordLayout();

    void tryAgain();
}
