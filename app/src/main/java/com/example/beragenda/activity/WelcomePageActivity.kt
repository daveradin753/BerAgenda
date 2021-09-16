package com.example.beragenda.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.beragenda.R

class WelcomePageActivity : AppCompatActivity() {

    private val SPLASH_TIME : Long = 1500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_page)

        Handler().postDelayed({
            startActivity(Intent (this, SignInSignUpActivity::class.java))
            finish()


        }, SPLASH_TIME)
    }
}