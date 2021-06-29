package com.example.beragenda.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.example.beragenda.R
import org.w3c.dom.Text

class SignInFragment : Fragment() {

    private lateinit var tvToSignUp: TextView
    private lateinit var tvForgotYourPasswordSignIn: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvToSignUp = view.findViewById(R.id.tvToSignUp)
        tvForgotYourPasswordSignIn = view.findViewById(R.id.tvForgotYourPasswordSignIn)

        val SignUpFragment = SignUpFragment()
        val ForgotPasswordFragment = ForgotPasswordFragment()
        val fragmentManager = fragmentManager

        tvToSignUp.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flSigninSignup, SignUpFragment)
                addToBackStack(null)
                commit()
            }
        }

        tvForgotYourPasswordSignIn.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flSigninSignup, ForgotPasswordFragment)
                addToBackStack(null)
                commit()
            }
        }


    }

    companion object {
        
    }
}