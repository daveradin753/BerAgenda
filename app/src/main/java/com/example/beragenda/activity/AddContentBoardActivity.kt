package com.example.beragenda.activity

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.beragenda.R
import com.example.beragenda.model.TasksBoard
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class AddContentBoardActivity : AppCompatActivity() {

    private lateinit var database: FirebaseFirestore
    private lateinit var backAddContentBoard: ImageView
    private lateinit var checklistAddContentBoard: ImageView
    private lateinit var tvAddCardContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_content_board)

        database = Firebase.firestore
        backAddContentBoard = findViewById(R.id.backAddContentBoard)
        checklistAddContentBoard = findViewById(R.id.checklistAddContentBoard)
        tvAddCardContent = findViewById(R.id.tvAddCardContent)
        val boardId = intent.getStringExtra("board_id").toString()

        backAddContentBoard.setOnClickListener {
            finish()
        }
        checklistAddContentBoard.setOnClickListener {
            val task_name = tvAddCardContent.text.toString()

            if (TextUtils.isEmpty(task_name)) {
                tvAddCardContent.error = getString(R.string.content_required)
                return@setOnClickListener
            }
            addDataCard(boardId, task_name)
            finish()
        }

    }

    private fun addDataCard(boardId: String, task_name: String) {
        val uuid: UUID = UUID.randomUUID()
        val task_id = uuid.toString()
        val type = 0
        val task = TasksBoard(task_id, task_name, type)

        database.collection("boards").document(boardId).collection("tasks").document(task_id)
            .set(task)
            .addOnSuccessListener {
                Log.d("ADD CARD", "Card has been added!")
            }
            .addOnFailureListener { e ->
                Log.e("ADD CARD", "Add card error!", e)
            }
    }
}