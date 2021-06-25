package com.example.beragenda.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.beragenda.R
import com.example.beragenda.fragment.SignInFragment

class SignInSignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)

//        actionBar?.hide()
        supportActionBar?.hide()

        val SignInFragment = SignInFragment()
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().apply {
            add(R.id.flSigninSignup, SignInFragment)
            addToBackStack(null)
            commit()
        }

    }
}