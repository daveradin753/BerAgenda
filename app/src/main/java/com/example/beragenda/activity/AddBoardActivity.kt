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

class AddBoardActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var ivBackAddBoard : ImageView
    private lateinit var ivChecklistAddBoard : ImageView
    private lateinit var etEnterTitleAddBoard : EditText
    private lateinit var ivProfilePictureAddBoard : ImageView
    private lateinit var ivWarna1 : ImageView
    private lateinit var ivWarna2 : ImageView
    private lateinit var ivWarna3 : ImageView
    private lateinit var ivWarna4 : ImageView
    private lateinit var ivWarna5 : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_board)

        database = Firebase.firestore
        ivBackAddBoard = findViewById(R.id.ivBackAddBoard)
        ivChecklistAddBoard = findViewById(R.id.ivChecklistAddBoard)
        etEnterTitleAddBoard = findViewById(R.id.etEnterTitleAddBoard)
        ivProfilePictureAddBoard = findViewById(R.id.ivProfilePictureAddBoard)
        ivWarna1 = findViewById(R.id.iv1)
        ivWarna2 = findViewById(R.id.iv2)
        ivWarna3 = findViewById(R.id.iv3)
        ivWarna4 = findViewById(R.id.iv4)
        ivWarna5 = findViewById(R.id.iv5)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        Log.d("UID", uid)


        ivBackAddBoard.setOnClickListener{
//            val intent = Intent(this, HomePageActivity::class.java)
//            startActivity(intent)
            finish()
        }

        ivChecklistAddBoard.setOnClickListener{
            addDataBoard(uid)
//            val intent = Intent(this, HomePageActivity::class.java)
//            startActivity(intent)
            finish()
        }

    }

    private fun addDataBoard(uid: String){

        val uuid : UUID = UUID.randomUUID()
        val board_id: String = uuid.toString()
        val project_name: String = etEnterTitleAddBoard.text.toString()
        val board = Boards(project_name, board_id, listOf(uid))

        database.collection("boards").document(board_id).set(board)
            .addOnSuccessListener {
                Log.d("ADD Board", "Board has been added!")
            }
            .addOnFailureListener{ e ->
                Log.e("ADD BOARD", "Add board error!", e)
            }
    }
}