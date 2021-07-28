package com.example.beragenda.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomePageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database : FirebaseFirestore
    private lateinit var drawer_layout: DrawerLayout
    private lateinit var nav_view: NavigationView
    private var dataBoards: MutableList<Boards> = ArrayList()
    private lateinit var rvBoards: RecyclerView
    private lateinit var btnToAddBoardActivity: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        checkTheme(this)

        auth = FirebaseAuth.getInstance()
        database = Firebase.firestore
        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        rvBoards = findViewById(R.id.rvBoards)
        btnToAddBoardActivity = findViewById(R.id.btnToAddBoardActivity)

        setSupportActionBar(findViewById(R.id.boardsPageToolBar))

        btnToAddBoardActivity.setOnClickListener {
            val intent = Intent(this, AddBoardActivity::class.java)
            startActivity(intent)
        }

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
        getDataBoards()

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

    private fun getDataBoards() {

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = BoardCustomAdapter(dataBoards)

        rvBoards.layoutManager = layoutManager
        rvBoards.adapter = adapter

        val uid = auth.currentUser?.uid.toString()
        database.collection("boards")
            .whereEqualTo("user_id", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val project_name = document.getString("project_name").toString()
                    val board_id = document.getString("board_id").toString()
//                    val test = document.data

                    Log.d("DATA BOARD", "Succesfully fetched data board!")
                    dataBoards.add(Boards(project_name, board_id))
                }
            }
            .addOnFailureListener {
                Log.e("DATA BOARD", "Failed fetch data board!", it)
            }
    }

}