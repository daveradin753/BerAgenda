package com.example.beragenda.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.beragenda.R
import com.example.beragenda.model.Boards
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class EditBoardActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var ivBackEditBoard : ImageView
    private lateinit var ivChecklistEditBoard : ImageView
    private lateinit var etEnterTitleEditBoard : EditText
    private lateinit var ivProfilePictureEditBoard : ImageView
    private lateinit var ivWarna1EditBoard : ImageView
    private lateinit var ivWarna2EditBoard : ImageView
    private lateinit var ivWarna3EditBoard : ImageView
    private lateinit var ivWarna4EditBoard : ImageView
    private lateinit var ivWarna5EditBoard : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_board)

        database = Firebase.firestore
        ivBackEditBoard = findViewById(R.id.ivBackEditBoard)
        ivChecklistEditBoard = findViewById(R.id.ivChecklistEditBoard)
        etEnterTitleEditBoard = findViewById(R.id.etEnterTitleEditBoard)
        ivProfilePictureEditBoard = findViewById(R.id.ivProfilePictureEditBoard)
        ivWarna1EditBoard = findViewById(R.id.iv1EditBoard)
        ivWarna2EditBoard = findViewById(R.id.iv2EditBoard)
        ivWarna3EditBoard = findViewById(R.id.iv3EditBoard)
        ivWarna4EditBoard = findViewById(R.id.iv4EditBoard)
        ivWarna5EditBoard = findViewById(R.id.iv5EditBoard)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        Log.d("UID", uid)

        ivBackEditBoard.setOnClickListener{
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }

        ivChecklistEditBoard.setOnClickListener{
            updateDataBoard(uid)
            val intent = Intent(this, HomePageActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun updateDataBoard(uid: String) {


    }
}