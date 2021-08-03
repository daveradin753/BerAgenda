package com.example.beragenda.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.beragenda.R
import com.example.beragenda.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpFragment : Fragment() {

    private lateinit var etSignUpEmailUser: EditText
    private lateinit var etSignUpUsernameUser: EditText
    private lateinit var etSignUpPasswordUser: EditText
    private lateinit var btnSignUp: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private var signInFragment = SignInFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etSignUpEmailUser = view.findViewById(R.id.etSignupEmailUser)
        etSignUpUsernameUser = view.findViewById(R.id.etSignupUsernameUser)
        etSignUpPasswordUser = view.findViewById(R.id.etSignupPasswordUser)
        btnSignUp = view.findViewById(R.id.btnSignUp)

        val SignInFragment = SignInFragment()
        val fragmentManager = fragmentManager
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        btnSignUp.setOnClickListener {
            val email: String = etSignUpEmailUser.text.toString()
            val password: String = etSignUpPasswordUser.text.toString()
            val username: String = etSignUpUsernameUser.text.toString()

            if (TextUtils.isEmpty(email)) {
                etSignUpEmailUser.error = "Email is required!"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(username)) {
                etSignUpUsernameUser.error = "Username is required!"
            }
            if (TextUtils.isEmpty(password)) {
                etSignUpPasswordUser.error = "Password is required!"
                return@setOnClickListener
            }
            if (password.length < 8) {
                etSignUpPasswordUser.error = "Password must be more than 8 characters!"
                return@setOnClickListener
            }
            signUp(email, password, username)
        }

    }

    private fun signUp(email: String, password: String, username: String) {
        val uid = auth.currentUser?.uid.toString()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val users = Users(uid, username, email)
                databaseReference.setValue(users)
                databaseReference = database.getReference("user").child(uid)
                Toast.makeText(this.context, "Register Success", Toast.LENGTH_SHORT).show()
                Log.d("REGISTER", "createUserWithEmailAndPassword:success")
                fragmentManager?.beginTransaction()?.apply {
                    replace(R.id.flSigninSignup, signInFragment)
                    addToBackStack(null)
                    commit()
                }

            } else {
                Toast.makeText(this.context, "Register failed", Toast.LENGTH_SHORT).show()
                Log.d("REGISTER", "createUserWithEmailAndPassword:failed")
            }
        }
    }

    companion object {

    }
}