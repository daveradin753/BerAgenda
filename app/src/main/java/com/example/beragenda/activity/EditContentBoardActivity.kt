package com.example.beragenda.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import com.example.beragenda.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditContentBoardActivity : AppCompatActivity() {

    private lateinit var database: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_content_board)

        database = Firebase.firestore
        val backEditContentBoard: ImageView = findViewById(R.id.backEditContentBoard)
        val checklistEditContentBoard: ImageView = findViewById(R.id.checklistEditContentBoard)
        val etEditCardContent: EditText = findViewById(R.id.etEditCardContent)

        val board_id_edit = intent.getStringExtra("board_id").toString()
        val task_name_edit = intent.getStringExtra("task_name").toString()
        val task_id_edit = intent.getStringExtra("task_id").toString()
        val type_edit = intent.getIntExtra("type", 0)

        etEditCardContent.setText(task_name_edit)

        backEditContentBoard.setOnClickListener {
            finish()
        }

        checklistEditContentBoard.setOnClickListener {
            val task_name_new: String = etEditCardContent.text.toString()
            editContentBoard(board_id_edit, task_id_edit, task_name_new)
            finish()
        }
    }

    private fun editContentBoard(board_id: String, task_id: String, task_name: String) {
        database.collection("boards").document(board_id).collection("tasks").document(task_id)
            .update("task_name", task_name)
            .addOnSuccessListener {
                Log.d("EDIT CARD", "Edit card $task_id on $board_id completed!")
            }
            .addOnFailureListener { e ->
                Log.e("EDIT CARD", "Edit card $task_id on $board_id failed!", e)
            }
    }
}