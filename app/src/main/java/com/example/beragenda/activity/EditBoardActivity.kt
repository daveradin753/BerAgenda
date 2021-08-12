package com.example.beragenda.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
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

        val board_id_edit: String = intent.getStringExtra("board_id").toString()
        val project_name_edit:String = intent.getStringExtra("project_name").toString()

        etEnterTitleEditBoard.setText(project_name_edit)

        ivBackEditBoard.setOnClickListener{
            finish()
        }

        ivChecklistEditBoard.setOnClickListener{
            val project_name_new: String = etEnterTitleEditBoard.text.toString()
            updateDataBoard(board_id_edit, project_name_new)
            finish()
        }

    }

    private fun updateDataBoard(board_id: String, project_name: String) {

        database.collection("boards").document(board_id)
            .update("project_name", project_name)
            .addOnSuccessListener {
                Toast.makeText(this, "Update board $project_name successfully!", Toast.LENGTH_SHORT)
                Log.d("UPDATE BOARD", "Update board $board_id | $project_name successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("UPDATE BOARD", "Update board $board_id | $project_name failed!", e)
            }
    }
}