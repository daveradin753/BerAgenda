package com.example.beragenda.activity

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.beragenda.R
import com.example.beragenda.adapter.BoardCustomAdapter
import com.example.beragenda.model.Boards
import com.example.beragenda.model.Users
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.w3c.dom.Text

class HomePageActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user: Users
    private lateinit var database : FirebaseFirestore
    private lateinit var drawer_layout: DrawerLayout
    private lateinit var nav_view: NavigationView
    private var dataBoards: MutableList<Boards> = ArrayList()
    private var dataUser: MutableList<Users> = ArrayList()
    private lateinit var rvBoards: RecyclerView
    private lateinit var btnToAddBoardActivity: View
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var tvUsername: TextView
    private lateinit var tvEmail: TextView
    private lateinit var pictureProfile: ImageView
    private lateinit var uid: String
    private val GALLERY_PICTURE_CODE = 1001
    var image_uri : Uri? = null
    var imageurl : Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        checkTheme(this)

        auth = FirebaseAuth.getInstance()
        database = Firebase.firestore
        uid = auth.currentUser?.uid.toString()

        drawer_layout = findViewById(R.id.drawer_layout)
        nav_view = findViewById(R.id.nav_view)
        rvBoards = findViewById(R.id.rvBoards)
        btnToAddBoardActivity = findViewById(R.id.btnToAddBoardActivity)
        val headerView = nav_view.getHeaderView(0)
        pictureProfile = headerView.findViewById(R.id.iv_ProfilePictureNavigation)

        tvEmail = headerView.findViewById(R.id.tvUsername)
        tvUsername = headerView.findViewById(R.id.tvNameNavigation)

        getUsername()
//        getDataBoards()
        setSupportActionBar(findViewById(R.id.boardsPageToolBar))

        btnToAddBoardActivity.setOnClickListener {
            val intent = Intent(this, AddBoardActivity::class.java)
            startActivity(intent)
        }

        pictureProfile.setOnClickListener {
            openGallery()
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

    }

    override fun onResume() {
        super.onResume()
        getDataBoards()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.actionbar_board, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        when (itemId) {

//            R.id.iconSearch -> {
//                Toast.makeText(applicationContext, "Search Toolbar Clicked", Toast.LENGTH_SHORT)
//                        .show()
//            }
//            R.id.iconNotification -> {
//                Toast.makeText(
//                        applicationContext,
//                        "Notification Toolbar Clicked",
//                        Toast.LENGTH_SHORT
//                ).show()
//            }
            R.id.logOut -> {
                val builder = AlertDialog.Builder(this)
                val uid = auth.currentUser?.uid

                builder.setMessage(R.string.logout_dialog)
                    .setPositiveButton(R.string.yes) { _, _ ->
                        logOut()
                        Toast.makeText(this, R.string.logout_success, Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton(R.string.no) { _, _ ->
                        Log.d("Logout Button", "Logout success: $uid")
                    }
                    .show()
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
        intent = Intent(this, SignInSignUpActivity::class.java)
        startActivity(intent)
        finish()
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
        dataBoards.clear()
        val uid = auth.currentUser?.uid.toString()

        database.collection("boards")
            .whereArrayContains("user_id", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val project_name = document.getString("project_name").toString()
                    val board_id = document.getString("board_id").toString()
                    val list: List<String> = listOf(document.get("user_id").toString())
                    val imageURL = document.getString("board_imageURL").toString()
                    val board_hex_color = document.getString("board_hex_color").toString()
//                    val test = document.data
//                    val project_name = test.get("project_name")

//                    Log.e("Test", "$project_name")
//                    Log.d("DATA BOARD", "Succesfully fetched data board!")
                    dataBoards.add(Boards(project_name, board_id, list, imageURL, board_hex_color))

                    val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    val adapter = BoardCustomAdapter(dataBoards)

                    rvBoards.layoutManager = layoutManager
                    rvBoards.setHasFixedSize(true)
                    rvBoards.adapter = adapter
                }
            }
            .addOnFailureListener {
                Log.e("DATA BOARD", "Failed fetch data board!", it)
            }
    }

    private fun getUsername(){
        dataUser.clear()

        database.collection("users")
            .whereEqualTo("uid", uid)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val username = document.getString("username").toString()
                    val profilePicUri = document.getString("profilePicURL").toString()
                    val email = document.getString("email").toString()
                    user = Users(uid,username,email,profilePicUri)

                    tvUsername.setText(user.username)
                    tvEmail.setText(user.email)
                    if (user.profilePicURL != "") {
                        pictureProfile.load(user.profilePicURL)
                    }
                    else {
                        pictureProfile.load(R.drawable.icons8_test_account_70px_3)
                    }
            }
        }

    }

    private fun openGallery(){
        val gallery = Intent()
        gallery.type = "image/*"
        gallery.action = Intent.ACTION_GET_CONTENT
        gallery.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)

        startActivityForResult(gallery, GALLERY_PICTURE_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == GALLERY_PICTURE_CODE){
            image_uri = data?.data
            Log.d("GET PICTURE", "Gallery picture : $image_uri")
            storage = FirebaseStorage.getInstance()
            storageReference = storage.getReference("userpictures/" + uid)
            val uploadTask = storageReference.putFile(image_uri!!)
            uploadTask.addOnSuccessListener {
                storageReference.downloadUrl.addOnCompleteListener {
                    imageurl = it.result
                    Log.d("UPLOAD PICTURE", "Upload picture: $imageurl")
                    pictureProfile.load(imageurl)
                    updateProfilePicture(uid, imageurl.toString())
                }
            }
            // set gallery image to image view
        }
    }

    private fun updateProfilePicture(uid: String, imageURL: String) {
        database.collection("users").document(uid)
            .update("profilePicURL", imageURL)
            .addOnSuccessListener {
                Toast.makeText(this, "Profile picture updated!", Toast.LENGTH_SHORT)
                Log.d("UPDATE BOARD", "Profile picture $uid updated!")
            }
            .addOnFailureListener {
                Log.d("UPDATE BOARD", "Profile picture $uid update error!")
            }
    }


}