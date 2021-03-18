package aslapov.android.study.pallada.kisuknd.raids.model

data class AuthFormState(val usernameError: Int? = null,
                          val passwordError: Int? = null,
                          val isDataValid: Boolean = false)