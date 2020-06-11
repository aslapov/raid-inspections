package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.net.ConnectException;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidApiFactory;
import aslapov.android.study.pallada.kisuknd.raids.model.transfer.LoggedInUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class AuthViewModel extends ViewModel {

	public enum LoginPhase {
		/**
		 * Первоначальная фаза авторизации, где требуется заполнить логин
		 */
		ENTERLOGIN,

		/**
		 * Логин заполнен. Требуется ввести пароль
		 */
		ENTERPASSWORD
	}

	public enum AuthResult {
		/**
		 * Аутентификация еще не проводилась
		 */
		NONE,

		/**
		 * Успешная аутентификация
		 */
		SUCCESS,

		/**
		 * Имя пользователя или пароль неверный
		 */
		FAIL,

		/**
		 * Произошла ошибка. Возможно на сервере
		 */
		ERROR,

		/**
		 * Сервер не доступен
		 */
		CONNECTIONERROR
	}

	public class ValidationField {
		private boolean isValid;
		@Nullable
		private Integer validationError;

		public ValidationField(boolean isValid) {
			this.isValid = isValid;
			this.validationError = null;
		}

		public ValidationField(boolean isValid, Integer validationError) {
			this.isValid = isValid;
			this.validationError = validationError;
		}

		public boolean isFieldValid() {
			return isValid;
		}

		@Nullable
		public Integer getValidationError() {
			return validationError;
		}
	}

	private MutableLiveData<ValidationField> mLoginField = new MutableLiveData<>();
	private MutableLiveData<ValidationField> mPasswordField = new MutableLiveData<>();
	private MutableLiveData<LoginPhase> mLoginPhase = new MutableLiveData<>();
	private MutableLiveData<AuthResult> mAuthResult = new MutableLiveData<>();

	public void init() {
		mLoginPhase.setValue(LoginPhase.ENTERLOGIN);
	}

	public LiveData<ValidationField> getLoginValid() {
		return mLoginField;
	}

	public LiveData<ValidationField> getPasswordValid() {
		return mPasswordField;
	}

	public LiveData<LoginPhase> getLoginPhase() {
		return mLoginPhase;
	}

	public LiveData<AuthResult> getAuthResult() {
		return mAuthResult;
	}

	public void tryAuth(String login, String password) {
		if (getLoginPhase().getValue() == LoginPhase.ENTERLOGIN && validateLogin(login)) {
			mLoginPhase.setValue(LoginPhase.ENTERPASSWORD);
		}
		if (getLoginPhase().getValue() == LoginPhase.ENTERPASSWORD && validatePassword(password)) {
			LoggedInUser user = new LoggedInUser(login, password);
			authorize(user);
			mLoginPhase.setValue(LoginPhase.ENTERLOGIN);
		}
	}

	private boolean validateLogin(String login) {
		boolean isValid = isUserNameValid(login);
		if (isValid)
			mLoginField.setValue(new ValidationField(true));
		else
			mLoginField.setValue(new ValidationField(false, R.string.invalid_username));
		return isValid;
	}

	private boolean validatePassword(String password) {
		boolean isValid = isPasswordValid(password);
		if (isValid)
			mPasswordField.setValue(new ValidationField(true));
		else
			mPasswordField.setValue(new ValidationField(false, R.string.invalid_password));
		return isValid;
	}

	private boolean isUserNameValid(String username) {
		if (username == null)
			return false;

		return !username.trim().isEmpty();
	}

	private boolean isPasswordValid(String password) {
		return password != null && password.trim().length() > 0;
	}

	@SuppressLint("CheckResult")
	private void authorize(LoggedInUser user) {
		RaidApiFactory.getRaidService().login(user)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(
						response -> {
							String ticket = response.getData().getTicket();
							RaidApiFactory.setAuthTicket(ticket);
							mAuthResult.setValue(AuthResult.SUCCESS);
						},
						error -> {
							if (error instanceof ConnectException) {
								mAuthResult.setValue(AuthResult.CONNECTIONERROR);
							} else if (error instanceof HttpException) {
								HttpException exception = (HttpException) error;
								switch (exception.code()) {
									case 400:
									case 401:
									case 403:
										mAuthResult.setValue(AuthResult.FAIL);
										break;
									default:
										mAuthResult.setValue(AuthResult.ERROR);
								}
							} else {
								mAuthResult.setValue(AuthResult.ERROR);
							}
						}
				);
	}
}
