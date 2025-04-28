package com.example.prog7313_poe.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.prog7313_poe.MainActivity
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.R

class LoginActivity : AppCompatActivity(){

    lateinit var inputEmail : EditText
    lateinit var  inputPassword : EditText
    lateinit var  loginButton : Button
    lateinit var user : User

    override fun onCreate (saveInstanceState: Bundle?){
        super.onCreate(saveInstanceState)

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
        setContentView(R.layout.login_activity)

        inputEmail = findViewById<EditText>(R.id.edit_email)
        inputPassword = findViewById<EditText>(R.id.edit_password)
        loginButton =  findViewById<Button>(R.id.btn_login)

        // Create a user
        user =  User("1","admin","0000")

        //
        loginButton.setOnClickListener{
            val email = inputEmail.text.toString()
            val password = inputPassword.text.toString()

            if(user.validateLogin(email, password)){
                Toast.makeText(this, "Login was successful!!!", Toast.LENGTH_SHORT).show()
                // Start MainActivity after login is successful
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()

            }else{
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
            }
        }

    }
}