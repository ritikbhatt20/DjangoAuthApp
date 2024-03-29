package com.example.djangoauthentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val registerBtn = findViewById<Button>(R.id.registerButton)
        val emailET = findViewById<EditText>(R.id.registerEmailEditText)
        val passwordET = findViewById<EditText>(R.id.registerPasswordEditText)
        val firstNameET = findViewById<EditText>(R.id.registerFirstNameEditText)
        val lastNameET = findViewById<EditText>(R.id.registerLastNameEditText)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.43.99:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create(AuthService::class.java)
        val authRepository = AuthRepository(authService)

        authViewModel = ViewModelProvider(
            this,
            AuthViewModelFactory(application, authRepository)
        )[AuthViewModel::class.java]

        registerBtn.setOnClickListener {

            val email = emailET.text.toString()
            val password = passwordET.text.toString()
            val firstName = firstNameET.text.toString()
            val lastName = lastNameET.text.toString()

            authViewModel.registerUser(email, password, firstName, lastName)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
