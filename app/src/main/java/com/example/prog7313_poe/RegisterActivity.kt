package com.example.prog7313_poe
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.data_access_object.UserDAO
import com.example.prog7313_poe.ui.loginRegister.LoginViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var user : User
    private lateinit var loginViewModel: LoginViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // Initialize views
        nameEditText = findViewById(R.id.edit_name)
        emailEditText = findViewById(R.id.edit_email)
        passwordEditText = findViewById(R.id.edit_password)
        surnameEditText = findViewById(R.id.edit_surname)
        registerButton = findViewById(R.id.btn_register)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        // Register button click listener
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val surname = surnameEditText.text.toString().trim()

            if (validateInput(name, email, password, surname)) {
                // Handle registration logic here
                // After successful registration, go back to login
                val user = User(0,email=email,password=password,name=name, surname=surname)
                loginViewModel.insertUser(user)

                // Validate Login
                loginViewModel.validateLogin(email, password).observe(this){ user ->
                    if(user != null){
                        Toast.makeText(this, "User registered", Toast.LENGTH_SHORT).show()


                    }else{
                        Toast.makeText(this, "Could not register user, please try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    private fun validateInput(fullName: String, email: String, password: String, confirmPassword: String): Boolean {
        if (fullName.isEmpty()) {
            nameEditText.error = "Name cannot be empty"
            return false
        }

        if (email.isEmpty()) {
            emailEditText.error = "Email cannot be empty"
            return false
        }

        if (password.isEmpty()) {
            passwordEditText.error = "Password cannot be empty"
            return false
        }

        if (confirmPassword.isEmpty()) {
            surnameEditText.error = "Surname cannot be empty"
            return false
        }


        return true
    }
}