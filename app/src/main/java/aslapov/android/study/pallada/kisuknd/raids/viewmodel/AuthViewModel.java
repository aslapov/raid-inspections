package aslapov.android.study.pallada.kisuknd.raids.viewmodel;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import aslapov.android.study.pallada.kisuknd.raids.R;
import aslapov.android.study.pallada.kisuknd.raids.model.AuthResult;
import aslapov.android.study.pallada.kisuknd.raids.model.RaidRepository;
import aslapov.android.study.pallada.kisuknd.raids.model.RepositoryProvider;

public class AuthViewModel extends ViewModel {

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

        public boolean isFieldValid() { return isValid; }

        @Nullable
        public Integer getValidationError() { return validationError; }
    }

    private MutableLiveData<AuthResult> authResult = new MutableLiveData<>();
    private MutableLiveData<ValidationField> loginField = new MutableLiveData<>();
    private MutableLiveData<ValidationField> passwordField = new MutableLiveData<>();

    public LiveData<AuthResult> getAuthResult() { return authResult; }

    public LiveData<ValidationField> getLoginValid() { return loginField; }

    public LiveData<ValidationField> getPasswordValid() { return passwordField; }

    public void tryAuth(String login, String password) {
        RaidRepository raidRepo = RepositoryProvider.provideRaidRepository();
        raidRepo.login(login, password, new RaidRepository.ResponseCallback<AuthResult>() {
            @Override
            public void onResponse(AuthResult result) {
                authResult.setValue(result);
            }

            @Override
            public void onError(Throwable t) {
            }
        });
    }

    public void validateLogin(String login) {
        if (isUserNameValid(login))
            loginField.setValue(new ValidationField(true));
        else
            loginField.setValue(new ValidationField(false, R.string.invalid_username));
    }

    public void validatePassword(String password) {
        if (isPasswordValid(password))
            passwordField.setValue(new ValidationField(true));
        else
            passwordField.setValue(new ValidationField(false, R.string.invalid_password));
    }

    private boolean isUserNameValid(String username) {
        if (username == null)
            return false;

        return !username.trim().isEmpty();
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
