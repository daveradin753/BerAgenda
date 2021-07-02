package com.example.beragenda.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import com.example.beragenda.R
import com.google.firebase.auth.FirebaseAuth

class HomePageActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        setSupportActionBar(findViewById(R.id.homeToolbar))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        when (itemId) {

            R.id.iconSearch -> {
                Toast.makeText(applicationContext, "Search Toolbar Clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.iconNotification -> {
                Toast.makeText(applicationContext, "Notification Toolbar Clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.logOut -> {
                logOut()
                intent = Intent(this, SignInSignUpActivity::class.java)
                startActivity(intent)
            }
        }

        return false

    }

    private fun logOut() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

}