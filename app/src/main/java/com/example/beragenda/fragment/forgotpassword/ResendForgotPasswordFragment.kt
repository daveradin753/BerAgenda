package com.example.beragenda.fragment.forgotpassword

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.beragenda.R
import com.example.beragenda.fragment.SignInFragment
import com.google.firebase.auth.FirebaseAuth


class ResendForgotPasswordFragment : Fragment() {

    private lateinit var btnToLoginFromResendPass: Button
    private lateinit var tvResendEmail: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resend_forgot_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnToLoginFromResendPass = view.findViewById(R.id.btnToLoginFromResendPass)
        tvResendEmail = view.findViewById(R.id.tvResendEmail)
        auth = FirebaseAuth.getInstance()

        val SignInFragment = SignInFragment()
        val email = arguments?.getString("forgotpasswordemail")

        btnToLoginFromResendPass.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flSigninSignup, SignInFragment)
                addToBackStack(null)
                commit()
            }
        }
        tvResendEmail.setOnClickListener {
            if (email != null) {      //kalo gaada, error emailnya
                auth.sendPasswordResetEmail(email).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Log.d("FORGOT PASS", "Email sent.")
                        Toast.makeText(this.context, "Email has been sent!", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }

    companion object {

    }
}