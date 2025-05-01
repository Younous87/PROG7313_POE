package com.example.prog7313_poe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.lifecycle.ViewModelProvider
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.R
import com.example.prog7313_poe.RegisterActivity
import com.example.prog7313_poe.ui.loginRegister.LoginViewModel

class LoginActivity : AppCompatActivity(){
    private lateinit var inputEmail : EditText
    private lateinit var inputPassword : EditText
    private lateinit var loginButton : Button
    private lateinit var user : User
    private lateinit var registerButton : Button
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate (savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        // Checks if user is already logged in
        /*
        val sharePref = getSharedPreferences("app_prefs", MODE_PRIVATE)
        val isLoggedIn = sharePref.getBoolean("is_logged_in", false)
        if(!isLoggedIn){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

         */

        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Initialize Views
        //---------------------------------------------------------------------------------------------------------------------------------------//
        inputEmail = findViewById<EditText>(R.id.edit_email)
        inputPassword = findViewById<EditText>(R.id.edit_password)
        loginButton =  findViewById<Button>(R.id.btn_login)
        registerButton = findViewById<Button>(R.id.btn_register)
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]


        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Login button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        loginButton.setOnClickListener {
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            // Check user validation
            if (validateInput(email, password)) {

                // Validate Login
                loginViewModel.validateLogin(email, password).observe(this){ user ->
                    if(user != null){
                        Toast.makeText(this, "Login successful! Welcome ${user.name}", Toast.LENGTH_SHORT).show()

                        // Start MainActivity after login is successful
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        // Close login activity
                        finish()
                    }else{
                        Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        //---------------------------------------------------------------------------------------------------------------------------------------//
        // Register button click Listener
        //---------------------------------------------------------------------------------------------------------------------------------------//
        registerButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }
    // Validate Login input
    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty()) {
            inputEmail.error = "Email cannot be empty"
            return false
        }

        if (password.isEmpty()) {
            inputPassword.error = "Password cannot be empty"
            return false
        }

        return true
    }

}