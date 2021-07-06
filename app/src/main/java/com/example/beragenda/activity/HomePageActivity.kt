package com.example.beragenda.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.beragenda.R
import com.google.firebase.auth.FirebaseAuth
import androidx.navigation.ui.NavigationUI.setupWithNavController as setupWithNavController1

class HomePageActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var drawer_layout : DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        drawer_layout = findViewById(R.id.drawer_layout)

        setSupportActionBar(findViewById(R.id.toolBar))

        val navController = Navigation.findNavController(this, R.id.fragment_container)
        setupWithNavController1(findViewById(R.id.nav_view), navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

        val toogle = ActionBarDrawerToggle(this, drawer_layout, findViewById(R.id.toolBar), R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)){
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
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