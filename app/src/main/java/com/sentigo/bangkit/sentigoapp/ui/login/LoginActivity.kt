package com.sentigo.bangkit.sentigoapp.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.databinding.ActivityLoginBinding
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.model.UserModel
import com.sentigo.bangkit.sentigoapp.ui.home.HomeActivity
import com.sentigo.bangkit.sentigoapp.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var factory: ViewModelFactory
    private val loginViewModel: LoginViewModel by viewModels { factory }

    private var clickBottomLogin = false
    private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = ViewModelFactory.getInstance(this)

        setupViewModel()
        setupAction()
    }

    private fun setupViewModel() {
        loginViewModel.loginResponse.observe(this) { user ->
            if (user != null) {
                when (user) {
                    is Result.Loading -> {
                        if (clickBottomLogin) binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        loginViewModel.saveUserPref(
                            UserModel(
                                user.data.userId,
                                user.data.token,
                                true
                            )
                        )
                        Toast.makeText(this, user.data.username, Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, user.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        loginViewModel.getUser.observe(this) { user ->
            if (user.isLogin && i == 0) {
                i++
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()

            when {
                email.isEmpty() -> binding.textLayoutEmail.error = getString(R.string.empty_email)
                password.isEmpty() || password.length < 8 -> binding.textLayoutPassword.error = getString(R.string.empty_password)
                else -> {
                    clickBottomLogin = true
                    loginViewModel.userLogin(email, password)
                }
            }
        }

        binding.signupButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}