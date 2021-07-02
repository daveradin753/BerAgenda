package com.example.beragenda.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.beragenda.R
import com.example.beragenda.fragment.SignInFragment
import kotlin.concurrent.fixedRateTimer

class SignInSignUpActivity : AppCompatActivity() {
    private val SignInFragment = SignInFragment()
    private val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin_signup)

//        actionBar?.hide()
        supportActionBar?.hide()


        fragmentManager.beginTransaction().apply {
            replace(R.id.flSigninSignup, SignInFragment)
            addToBackStack(null)
            commit()
        }

    }

}