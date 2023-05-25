package com.sentigo.bangkit.sentigoapp.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.sentigo.bangkit.sentigoapp.R
import com.sentigo.bangkit.sentigoapp.databinding.ActivityRegisterBinding
import com.sentigo.bangkit.sentigoapp.di.Result
import com.sentigo.bangkit.sentigoapp.di.ViewModelFactory
import com.sentigo.bangkit.sentigoapp.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    private lateinit var factory: ViewModelFactory
    private val registerViewModel: RegisterViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory = ViewModelFactory.getInstance(this)

        registerViewModel.registerResponse.observe(this) { user ->
            if (user != null) {
                when (user) {
                    is Result.Loading -> {
                        // loading tampil
                    }

                    is Result.Success -> {
                        Toast.makeText(this, user.data.message, Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }

                    is Result.Error -> {
                        Toast.makeText(this, user.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        setupAction()
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val username = binding.edUsername.text.toString()
            val email = binding.edEmail.text.toString()
            val password = binding.edPassword.text.toString()
            when {
                username.isEmpty() -> binding.textLayoutUsername.error = getString(R.string.empty_username)
                email.isEmpty() -> binding.textLayoutEmail.error = getString(R.string.empty_email)
                password.isEmpty() -> binding.textLayoutPassword.error = getString(R.string.empty_password)
                password.length >= 8 -> {
                    registerViewModel.registerUser(username, email, password)
                }
            }
        }
    }
}