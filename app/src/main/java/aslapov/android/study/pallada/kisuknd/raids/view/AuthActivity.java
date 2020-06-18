package aslapov.android.study.pallada.kisuknd.raids.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textview.MaterialTextView;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.AuthViewModel;
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory;

public class AuthActivity extends AppCompatActivity {

	// Сохранение видимости компоненты ввода пароля после изменения конфигурации Activity
	private static final String SAVED_PASSWORD_INPUT_VISIBLE = "passwordInputVisible";

	private AuthViewModel mViewModel;

	private EditText mLogin;
	private EditText mPassword;
	private MaterialTextView mAuthErrorTextView;
	private ProgressBar mLoading;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.authorization_layout);

		mViewModel = new ViewModelProvider(this, new ViewModelFactory(getApplication())).get(AuthViewModel.class);
		mViewModel.init();

		mLogin = (EditText) findViewById(R.id.login);
		mPassword = (EditText) findViewById(R.id.password);
		mAuthErrorTextView = (MaterialTextView) findViewById(R.id.auth_message);
		Button mAuthButton = (Button) findViewById(R.id.auth);
		mLoading = findViewById(R.id.loading);

		if (savedInstanceState != null && savedInstanceState.getBoolean(SAVED_PASSWORD_INPUT_VISIBLE))
			showPasswordLayout();

		Observer<AuthViewModel.ValidationField> validationField = field -> {
			if (!field.isFieldValid()) {
				mLoading.setVisibility(View.GONE);
				showValidationError(getString(field.getValidationError()));
			}
		};

		mViewModel.getLoginValid().observe(this, validationField);
		mViewModel.getPasswordValid().observe(this, validationField);

		mViewModel.getLoginPhase().observe(this, phase -> {
			mLoading.setVisibility(View.GONE);
		    if (phase == AuthViewModel.LoginPhase.ENTERLOGIN)
		        clearView();
            if (phase == AuthViewModel.LoginPhase.ENTERPASSWORD)
		        showPasswordLayout();
        });

		mAuthButton.setOnClickListener(view -> {
			mLoading.setVisibility(View.VISIBLE);
			mAuthErrorTextView.setText("");
			String userName = mLogin.getText().toString();
			String password = mPassword.getText().toString();
			mViewModel.tryAuth(userName, password);
		});

		mViewModel.getAuthResult().observe(this, authResult -> {
			mLoading.setVisibility(View.GONE);
			switch (authResult) {
				case FAIL:
					mAuthErrorTextView.setText(getString(R.string.authorization_fail));
					break;
				case ERROR:
					mAuthErrorTextView.setText(getString(R.string.authorization_error));
					break;
				case CONNECTIONERROR:
					mAuthErrorTextView.setText(getString(R.string.authorization_connect_error));
					break;
				case SUCCESS:
					openRaidListScreen();
					break;
			}
		});
	}

	private void openRaidListScreen() {
		mLoading.setVisibility(View.VISIBLE);
		RaidListActivity.start(this);
		setResult(RESULT_OK);
		finish();
	}

	private void showValidationError(String message) {
		if (mLogin.getVisibility() == View.VISIBLE)
			mLogin.setError(message);
		else
			mPassword.setError(message);
	}

	private void clearView() {
		mLogin.setText("");
		mPassword.setText("");
		mAuthErrorTextView.setText("");
		mLogin.setVisibility(View.VISIBLE);
		mPassword.setVisibility(View.INVISIBLE);
	}

	private void showPasswordLayout() {
		mLogin.setVisibility(View.INVISIBLE);
		mPassword.setVisibility(View.VISIBLE);
		mPassword.requestFocus();
	}

	@Override
	protected void onSaveInstanceState(@NonNull Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(SAVED_PASSWORD_INPUT_VISIBLE, mPassword.getVisibility() == View.VISIBLE);
	}
}
