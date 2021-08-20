package com.example.beragenda.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.beragenda.R
import com.example.beragenda.fragment.SignInFragment
import com.google.firebase.auth.FirebaseAuth

class SignInSignUpActivity : AppCompatActivity() {
    private val SignInFragment = SignInFragment()
    private val fragmentManager = supportFragmentManager
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)

//        actionBar?.hide()
//        supportActionBar?.hide()

        fragmentManager.beginTransaction().apply {
            replace(R.id.flSigninSignup, SignInFragment)
            addToBackStack(null)
            commit()
        }

    }

    override fun onResume() {
        super.onResume()
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            finish()
        }

    }

}