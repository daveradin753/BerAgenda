package com.example.beragenda.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.adapter.BoardCustomAdapter
import com.example.beragenda.model.Boards
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth

class HomePageActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var drawer_layout: DrawerLayout
    private lateinit var nav_view: NavigationView
    private var dataBoards: MutableList<Boards> = ArrayList()
    private lateinit var rvBoards: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        checkTheme(this)

        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        rvBoards = findViewById(R.id.rvBoards)

        setSupportActionBar(findViewById(R.id.boardsPageToolBar))

        val toogle = ActionBarDrawerToggle(
                this,
                drawer_layout,
                findViewById(R.id.boardsPageToolBar),
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toogle)
        toogle.syncState()

        NavigationDrawer()
        addBoards()

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = BoardCustomAdapter(dataBoards)

        rvBoards.layoutManager = layoutManager
        rvBoards.adapter = adapter

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
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
                Toast.makeText(applicationContext, "Search Toolbar Clicked", Toast.LENGTH_SHORT)
                        .show()
            }
            R.id.iconNotification -> {
                Toast.makeText(
                        applicationContext,
                        "Notification Toolbar Clicked",
                        Toast.LENGTH_SHORT
                ).show()
            }
            R.id.logOut -> {
                logOut()
            }
        }
        return false

    }

    private fun NavigationDrawer() {
        nav_view = findViewById(R.id.nav_view)
        nav_view.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_settings -> {
                    Toast.makeText(
                            applicationContext,
                            "Notification Toolbar Clicked",
                            Toast.LENGTH_SHORT
                    ).show()
                    intent = Intent(this, SettingActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        })
    }

    private fun logOut() {
        val sharedPreferences = getSharedPreferences("modeClick", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        auth = FirebaseAuth.getInstance()
        auth.signOut()
//        finish()
        intent = Intent(this, SignInSignUpActivity::class.java)
        startActivity(intent)
    }

    private fun checkTheme(context: Context) {
        val DARK_MODE = "dark_mode"
        val int = context.getSharedPreferences("modeClick", Context.MODE_PRIVATE)
        when (int.getInt(DARK_MODE, 0)) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }
        }
    }

    private fun addBoards() {

    }

}