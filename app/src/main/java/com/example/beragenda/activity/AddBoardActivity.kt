package com.example.beragenda.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import coil.load
import com.example.beragenda.R
import com.example.beragenda.model.Boards
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class AddBoardActivity : AppCompatActivity() {

    private lateinit var storage : FirebaseStorage
    private lateinit var storageReference : StorageReference
    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var ivBackAddBoard : ImageView
    private lateinit var ivChecklistAddBoard : ImageView
    private lateinit var etEnterTitleAddBoard : EditText
    private lateinit var ivProfilePictureAddBoard : ImageView
    private lateinit var btnAddBoardPicture : Button
    private lateinit var ivWarna1 : ImageView
    private lateinit var ivWarna2 : ImageView
    private lateinit var ivWarna3 : ImageView
    private lateinit var ivWarna4 : ImageView
    private lateinit var ivWarna5 : ImageView
    private val GALLERY_PICTURE_CODE = 1001
    var image_uri : Uri? = null
    var imageurl : Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_board)

        database = Firebase.firestore
        ivBackAddBoard = findViewById(R.id.ivBackAddBoard)
        ivChecklistAddBoard = findViewById(R.id.ivChecklistAddBoard)
        etEnterTitleAddBoard = findViewById(R.id.etEnterTitleAddBoard)
        ivProfilePictureAddBoard = findViewById(R.id.ivProfilePictureAddBoard)
        btnAddBoardPicture = findViewById(R.id.btnAddBoardPicture)
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
            addDataBoard(uid, imageurl!!)
//            val intent = Intent(this, HomePageActivity::class.java)
//            startActivity(intent)
            finish()
        }

        btnAddBoardPicture.setOnClickListener{
            openGallery()
        }

    }

    private fun addDataBoard(uid: String, imageurl: Uri){

        val uuid : UUID = UUID.randomUUID()
        val board_id: String = uuid.toString()
        val project_name: String = etEnterTitleAddBoard.text.toString()
        val board = Boards(project_name, board_id, listOf(uid), imageurl.toString())

        database.collection("boards").document(board_id).set(board)
            .addOnSuccessListener {
                Log.d("ADD Board", "Board has been added!")
            }
            .addOnFailureListener{ e ->
                Log.e("ADD BOARD", "Add board error!", e)
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
            storageReference = storage.getReference("boardpicture/" + etEnterTitleAddBoard.text.toString())
            val uploadTask = storageReference.putFile(image_uri!!)
            uploadTask.addOnSuccessListener {
                storageReference.downloadUrl.addOnCompleteListener {
                    imageurl = it.result
                    Log.d("UPLOAD PICTURE", "Upload picture: $imageurl")
                    ivProfilePictureAddBoard.load(imageurl)
                }
            }
            // set gallery image to image view
        }
    }

}