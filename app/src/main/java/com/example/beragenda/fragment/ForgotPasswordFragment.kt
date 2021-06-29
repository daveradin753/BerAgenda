package com.example.beragenda.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.beragenda.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    private lateinit var tvLoginForgotYourPassword : TextView
    private lateinit var btnSendForgotYourPassword : Button
    private lateinit var etForgotYourPasswordEmailUser : EditText
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvLoginForgotYourPassword = view.findViewById(R.id.tvLoginForgotYourPassword)
        btnSendForgotYourPassword = view.findViewById(R.id.btnSendForgotYourPassword)
        etForgotYourPasswordEmailUser = view.findViewById(R.id.etForgotYourPasswordEmailUser)
        auth = FirebaseAuth.getInstance()

        val SignInFragment = SignInFragment()
        val ResendForgotPasswordFragment = ResendForgotPasswordFragment()
        val fragmentManager = fragmentManager

        tvLoginForgotYourPassword.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flSigninSignup, SignInFragment)
                addToBackStack(null)
                commit()
            }
        }

        btnSendForgotYourPassword.setOnClickListener {
            val email : String = etForgotYourPasswordEmailUser.text.toString()
            if (TextUtils.isEmpty(email)) {
                etForgotYourPasswordEmailUser.error = "Email is required!"
                return@setOnClickListener
            }
            auth.sendPasswordResetEmail(etForgotYourPasswordEmailUser.text.toString()).addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("FORGOT PASS", "Email sent.")
                    val bundle = Bundle()
                    bundle.putString("forgotpasswordemail", email)
                    val ResendForgotPasswordFragment = ResendForgotPasswordFragment()
                    ResendForgotPasswordFragment.arguments = bundle
                    fragmentManager?.beginTransaction()?.apply {
                        replace(R.id.flSigninSignup, ResendForgotPasswordFragment)
                        addToBackStack(null)
                        commit()
                    }
                }

            }
        }

    }

    companion object {

    }
}