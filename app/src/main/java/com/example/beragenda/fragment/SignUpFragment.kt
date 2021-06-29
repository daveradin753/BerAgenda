package com.example.beragenda.fragment

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.beragenda.R
import kotlin.concurrent.fixedRateTimer

class SignUpFragment : Fragment() {

    private lateinit var tvForgotYourPasswordSignUp: TextureView
    private lateinit var etSignUpEmailUser: EditText
    private lateinit var etSignUpPasswordUser: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvForgotYourPasswordSignUp = view.findViewById(R.id.tvForgotYourPasswordSignup)
        etSignUpEmailUser = view.findViewById(R.id.etSignupEmailUser)
        etSignUpPasswordUser = view.findViewById(R.id.etSignupPasswordUser)

        val ForgotPasswordFragment = ForgotPasswordFragment()
        val fragmentManager = fragmentManager

        tvForgotYourPasswordSignUp.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flSigninSignup,ForgotPasswordFragment)
                addToBackStack(null)
                commit()
            }
        }
    }

    companion object {

    }
}