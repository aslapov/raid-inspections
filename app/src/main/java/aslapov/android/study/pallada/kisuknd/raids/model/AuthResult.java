package aslapov.android.study.pallada.kisuknd.raids.model;

public class AuthResult {

    /**
     * Состояние авторизации
     */
    public enum State {
        /**
         * Правильный логин
          */
        LOGINED,

        /**
         * Ошибка логина или пароля
          */
        WRONG,

        /**
         * Пользователь найден в системе КИСУ. Регистрация
         */
        REGISTRATION,

        /**
         * Пользователь зарегистрирован
         */
        REGISTERED,

        /**
         * Неизвестная ошибка
         */
        FAIL,

        /**
         * Успешная авторизация
         */
        SUCCESS;
    }

    private State authState;

    public State getAuthState() {
        return authState;
    }

    void setAuthState(State authState) {
        this.authState = authState;
    }
}
