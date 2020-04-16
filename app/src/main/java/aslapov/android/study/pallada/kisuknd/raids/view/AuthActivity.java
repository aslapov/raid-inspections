package aslapov.android.study.pallada.kisuknd.raids.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.AuthViewModel;

public class AuthActivity extends AppCompatActivity {

    // Сохранение видимости компоненты ввода пароля после изменения конфигурации Activity
    private static final String SAVED_PASSWORD_INPUT_VISIBLE = "passwordInputVisible";

    private AuthViewModel mViewModel;

    private EditText mLogin;
    private EditText mPassword;
    private ProgressBar mLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_layout);
        mViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);

        mLogin = (EditText) findViewById(R.id.login);
        mPassword = (EditText) findViewById(R.id.password);
        Button mAuthButton = (Button) findViewById(R.id.auth);
        mLoading = findViewById(R.id.loading);

        if (savedInstanceState != null && savedInstanceState.getBoolean(SAVED_PASSWORD_INPUT_VISIBLE))
            showPasswordLayout();

        TextWatcher passwordTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.validatePassword(mPassword.getText().toString());
            }
        };

        Observer<AuthViewModel.ValidationField> validationField = field -> {
            if (field.isFieldValid()) {
                mAuthButton.setEnabled(true);
            } else {
                mLoading.setVisibility(View.GONE);
                showAuthMessage(getString(field.getValidationError()));
                mAuthButton.setEnabled(false);
            }
        };

        mViewModel.getLoginValid().observe(this, validationField);
        mViewModel.getPasswordValid().observe(this, validationField);

        mViewModel.getAuthResult().observe(this, authResult -> {
            if (authResult == null)
                return;

            mLoading.setVisibility(View.GONE);
            mPassword.removeTextChangedListener(passwordTextWatcher);
            switch (authResult.getAuthState()) {
                case LOGINED:
                    showPasswordLayout();
                    break;
                case WRONG:
                    clearView();
                    showAuthMessage(getString(R.string.authorization_wrong));
                    break;
                case REGISTRATION:
                    showPasswordLayout();
                    mPassword.addTextChangedListener(passwordTextWatcher);
                    showAuthMessage(getString(R.string.authorization_registration));
                    break;
                case REGISTERED:
                    showAuthMessage(getString(R.string.authorization_registrated));
                    break;
                case FAIL:
                    showAuthMessage(getString(R.string.authorization_fail));
                    break;
                case SUCCESS:
                    openRaidListScreen();
                    break;
            }
        });

        mLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                mViewModel.validateLogin(mLogin.getText().toString());
            }
        });

        mAuthButton.setOnClickListener(view -> {
            mLoading.setVisibility(View.VISIBLE);
            String userName = mLogin.getText().toString();
            String password = mPassword.getText().toString();
            mViewModel.tryAuth(userName, password);
        });
    }

    private void openRaidListScreen() {
        RaidListActivity.start(this);
        setResult(RESULT_OK);
        finish();
    }

    private void showAuthMessage(String message) {
        if (mLogin.getVisibility() == View.VISIBLE)
            mLogin.setError(message);
        else
            mPassword.setError(message);
    }

    private void clearView() {
        mLogin.setText("");
        mPassword.setText("");
        mLogin.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.INVISIBLE);
    }

    private void showPasswordLayout() {
        mLogin.setVisibility(View.INVISIBLE);
        mPassword.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_PASSWORD_INPUT_VISIBLE, mPassword.getVisibility() == View.VISIBLE);
    }
}
