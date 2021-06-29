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
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.beragenda.activity.HomePageActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.w3c.dom.Text

class SignUpFragment : Fragment() {

    private lateinit var tvForgotYourPasswordSignUp: TextureView
    private lateinit var etSignUpEmailUser: EditText
    private lateinit var etSignUpPasswordUser: EditText
    private lateinit var btnSignUp: Button
    private lateinit var auth: FirebaseAuth
    private var signInFragment= SignInFragment()

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
        btnSignUp = view.findViewById(R.id.btnSignUp)

        val ForgotPasswordFragment = ForgotPasswordFragment()
        val fragmentManager = fragmentManager

        tvForgotYourPasswordSignUp.setOnClickListener {
            fragmentManager?.beginTransaction()?.apply {
                replace(R.id.flSigninSignup, ForgotPasswordFragment)
                addToBackStack(null)
                commit()
            }
        }

        btnSignUp.setOnClickListener {
            val email: String = etSignUpEmailUser.text.toString()
            val password: String = etSignUpPasswordUser.text.toString()

            if (TextUtils.isEmpty(email)) {
                etSignUpEmailUser.error = "Email is required!"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                etSignUpPasswordUser.error = "Password is required!"
                return@setOnClickListener
            }
            if (password.length < 8) {
                etSignUpPasswordUser.error = "Password must be more than 8 characters!"
                return@setOnClickListener
            }
            signUp(email, password)
        }
    }

        private fun signUp(email: String, password: String) {
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                if(it.isSuccessful) {
                    Toast.makeText(this.context,"Register Success", Toast.LENGTH_SHORT).show()
                    Log.d("REGISTER", "createUserWithEmailAndPassword:success")
                    childFragmentManager?.beginTransaction()?.apply {
                        replace(R.id.flSigninSignup, signInFragment)
                        addToBackStack(null)
                        commit()
                    }

            }else{
                    Toast.makeText(this.context,"Register failed", Toast.LENGTH_SHORT).show()
                    Log.d("REGISTER", "createUserWithEmailAndPassword:failed")
                }
        }
    }

    companion object {

    }
}