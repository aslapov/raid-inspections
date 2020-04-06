package aslapov.android.study.pallada.kisuknd.raids.view.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.presenter.AuthPresenter;
import aslapov.android.study.pallada.kisuknd.raids.view.ILoadingView;
import aslapov.android.study.pallada.kisuknd.raids.view.raidsList.RaidListActivity;

public class AuthActivity extends AppCompatActivity implements IAuthView {

    // Сохранение видимости компоненты ввода пароля после изменения конфигурации Activity
    private static final String SAVED_PASSWORD_INPUT_VISIBLE = "passwordInputVisible";

    private AuthPresenter mPresenter;
    private EditText mLogin;
    private EditText mPassword;
    private ILoadingView mLoadingView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_layout);

        //mLoadingView = LoadingDialog.view(getSupportFragmentManager());

        if (mPresenter == null)
            mPresenter = new AuthPresenter();
        mPresenter.attachView(this);

        mLogin = (EditText) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);
        Button authButton = (Button) findViewById(R.id.auth);

        if (savedInstanceState != null && savedInstanceState.getBoolean(SAVED_PASSWORD_INPUT_VISIBLE))
            showPasswordLayout();

        authButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = mLogin.getText().toString();
                String password = mPassword.getText().toString();
                mPresenter.tryAuth(userName, password);
            }
        });
    }

    @Override
    public void openRaidListScreen() {
        RaidListActivity.start(this);
        finish();
    }

    @Override
    public void showAuthInfo(String message) {
        if (mLogin.getVisibility() == View.VISIBLE)
            mLogin.setError(message);
        else
            mPassword.setError(message);
    }

    @Override
    public void showAuthError(String message) {
        if (mLogin.getVisibility() == View.VISIBLE)
            mLogin.setError(message);
        else
            mPassword.setError(message);
    }

    @Override
    public void clearView() {
        mLogin.setText("");
        mPassword.setText("");
        mLogin.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showPasswordLayout() {
        mLogin.setVisibility(View.INVISIBLE);
        mPassword.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoading() {
        mLoadingView.showLoading();
    }

    @Override
    public void hideLoading() {
        mLoadingView.hideLoading();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_PASSWORD_INPUT_VISIBLE, mPassword.getVisibility() == View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
