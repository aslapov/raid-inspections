package aslapov.android.study.pallada.kisuknd.raids.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import aslapov.android.study.pallada.kisuknd.raids.R
import aslapov.android.study.pallada.kisuknd.raids.view.RaidListActivity
import aslapov.android.study.pallada.kisuknd.raids.viewmodel.ViewModelFactory

private lateinit var authViewModel: AuthViewModel

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.authorization_layout)

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val loading = findViewById<ProgressBar>(R.id.loading)
        val error = findViewById<TextView>(R.id.auth_message)

        authViewModel = ViewModelProvider(this, ViewModelFactory(application))
                .get(AuthViewModel::class.java)

        username.doAfterTextChanged {
            authViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        password.doAfterTextChanged {
            authViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
            )
        }

        login.setOnClickListener {
            loading.visibility = View.VISIBLE
            authViewModel.login(username.text.toString(), password.text.toString())
        }

        authViewModel.authFormState.observe(this@AuthActivity, Observer {
            val authState = it ?: return@Observer

            login.isEnabled = authState.isDataValid

            if (authState.usernameError != null) {
                username.error = getString(authState.usernameError)
            }
            if (authState.passwordError != null) {
                password.error = getString(authState.passwordError)
            }
        })

        authViewModel.authResult.observe(this@AuthActivity, Observer {
            val authResult = it ?: return@Observer

            loading.visibility = View.GONE
            when (authResult) {
                AuthViewModel.AuthResult.ERROR -> error.text = getString(R.string.authorization_fail)
                AuthViewModel.AuthResult.SUCCESS -> {
                    loading.visibility = View.VISIBLE
                    openRaidListScreen()
                }
            }
        })
    }

    private fun openRaidListScreen() {
        RaidListActivity.start(this)
        setResult(RESULT_OK)
        finish()
    }
}