package com.example.beragenda.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import coil.load
import com.example.beragenda.R
import com.example.beragenda.model.Boards
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
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
    private lateinit var ivColorPickerAddBoard: ImageView
    private val GALLERY_PICTURE_CODE = 1001
    var image_uri : Uri? = null
    var imageurl : Uri? = null
    var hexColor : String = "#9EF5CF"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_board)

        database = Firebase.firestore
        ivBackAddBoard = findViewById(R.id.ivBackAddBoard)
        ivChecklistAddBoard = findViewById(R.id.ivChecklistAddBoard)
        etEnterTitleAddBoard = findViewById(R.id.etEnterTitleAddBoard)
        ivProfilePictureAddBoard = findViewById(R.id.ivProfilePictureAddBoard)
        btnAddBoardPicture = findViewById(R.id.btnAddBoardPicture)
        ivColorPickerAddBoard = findViewById(R.id.ivColorPickerAddBoard)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        Log.d("UID", uid)


        ivBackAddBoard.setOnClickListener{
            finish()
        }

        ivColorPickerAddBoard.setOnClickListener {
            MaterialColorPickerDialog.Builder(this)
                .setTitle("Pick Color")
                .setColorShape(ColorShape.CIRCLE)
                .setColorRes(resources.getIntArray(R.array.boardColorBox))
                .setColorListener { color, colorHex ->
                    ivColorPickerAddBoard.setBackgroundColor(Color.parseColor(colorHex))
                    hexColor = colorHex
                }
                .show()
        }

        ivChecklistAddBoard.setOnClickListener{
            val uuid : UUID = UUID.randomUUID()
            val board_id: String = uuid.toString()
            val project_name: String = etEnterTitleAddBoard.text.toString()

            if (TextUtils.isEmpty(project_name)) {
                etEnterTitleAddBoard.error = getString(R.string.board_title_required)
                return@setOnClickListener
            }
            if (project_name.length >= 24) {
                etEnterTitleAddBoard.error = getString(R.string.board_title_too_long)
                return@setOnClickListener
            }
            addDataBoard(uid, project_name, board_id, hexColor)

            finish()
        }

        btnAddBoardPicture.setOnClickListener{
            openGallery()
        }

    }

    private fun addDataBoard(uid: String, project_name: String, board_id: String, hexColor: String){

        val board = Boards(project_name, board_id, listOf(uid), imageurl.toString(), hexColor)

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