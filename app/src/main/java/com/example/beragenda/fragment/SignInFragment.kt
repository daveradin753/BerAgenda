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
import androidx.fragment.app.FragmentManager
import com.example.beragenda.R
import com.example.beragenda.activity.HomePageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import org.w3c.dom.Text

class SignInFragment : Fragment() {

    private lateinit var tvToSignUp: TextView
    private lateinit var tvForgotYourPasswordSignIn: TextView
    private lateinit var etSignInEmailUser: EditText
    private lateinit var etSignInPasswordUser: EditText
    private lateinit var btnSignin: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvToSignUp = view.findViewById(R.id.tvToSignUp)
        tvForgotYourPasswordSignIn = view.findViewById(R.id.tvForgotYourPasswordSignIn)
        etSignInEmailUser = view.findViewById(R.id.etSigninEmailUser)
        etSignInPasswordUser = view.findViewById(R.id.etSigninPasswordUser)
        btnSignin = view.findViewById(R.id.btnSignIn)

        val SignUpFragment = SignUpFragment()
        val ForgotPasswordFragment = ForgotPasswordFragment()
        val fragmentManager = childFragmentManager

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

        btnSignin.setOnClickListener {
            val email: String = etSignInEmailUser.text.toString()
            val password: String = etSignInPasswordUser.text.toString()

            if (TextUtils.isEmpty(email)) {
                etSignInEmailUser.error = "Email is required!"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                etSignInPasswordUser.error = "Password is required!"
                return@setOnClickListener
            }
            if (password.length < 8){
                etSignInPasswordUser.error = "Password must be more than 8 characters!"
                return@setOnClickListener
            }
            signIn(email, password)
        }


    }

    private fun signIn(email: String, password: String) {
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(this.context,"Login Success", Toast.LENGTH_SHORT).show()
                    Log.d("LOGIN", "signInWithEmail:success")
                    val intent = Intent(this.context, HomePageActivity::class.java)
                    startActivity(intent)
                }else {
                    Toast.makeText(this.context,"Login failed", Toast.LENGTH_SHORT).show()
                    Log.d("LOGIN", "signInWithEmail:failed")
                }
            }
    }

    companion object {
        
    }
}