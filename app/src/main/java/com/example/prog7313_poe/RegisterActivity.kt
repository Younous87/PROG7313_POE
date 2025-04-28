package com.example.prog7313_poe
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.prog7313_poe.classes.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var user : User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        // Initialize views
        nameEditText = findViewById(R.id.edit_name)
        emailEditText = findViewById(R.id.edit_email)
        passwordEditText = findViewById(R.id.edit_password)
        surnameEditText = findViewById(R.id.edit_surname)
        registerButton = findViewById(R.id.btn_register)

        // Register button click listener
        registerButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val surname = surnameEditText.text.toString().trim()

            if (validateInput(name, email, password, surname)) {
                // Handle registration logic here
                // After successful registration, go back to login
                if(user.createUser(name,surname, email,password)){
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()
                   // finish() // Return to LoginActivity
                }else{
                    Toast.makeText(this, "Could not create account, Please try again!", Toast.LENGTH_SHORT).show()
                }
                //finish() // Return to LoginActivity
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