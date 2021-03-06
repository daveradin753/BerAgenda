package com.example.beragenda.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.beragenda.R
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*

class EditBoardActivity : AppCompatActivity() {

    private lateinit var database : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private lateinit var ivBackEditBoard : ImageView
    private lateinit var ivChecklistEditBoard : ImageView
    private lateinit var etEnterTitleEditBoard : EditText
    private lateinit var ivProfilePictureEditBoard : ImageView
    private lateinit var btnEditBoardPicture : Button
    private lateinit var project_name_edit: String
    private lateinit var storage : FirebaseStorage
    private lateinit var storageReference : StorageReference
    private lateinit var ivColorPickerEditBoard: ImageView
    private val GALLERY_PICTURE_CODE = 1001
    var image_uri : Uri? = null
    var imageurl : Uri? = null
    var hexColor : String = "#9EF5CF"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_board)

        database = Firebase.firestore
        ivBackEditBoard = findViewById(R.id.ivBackEditBoard)
        ivChecklistEditBoard = findViewById(R.id.ivChecklistEditBoard)
        etEnterTitleEditBoard = findViewById(R.id.etEnterTitleEditBoard)
        ivProfilePictureEditBoard = findViewById(R.id.ivProfilePictureEditBoard)
        btnEditBoardPicture = findViewById(R.id.btnEditBoardPicture)
        ivProfilePictureEditBoard = findViewById(R.id.ivProfilePictureEditBoard)
        ivColorPickerEditBoard = findViewById(R.id.ivColorPickerEditBoard)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        Log.d("UID", uid)

        val board_id_edit: String = intent.getStringExtra("board_id").toString()
        project_name_edit = intent.getStringExtra("project_name").toString()
        val board_picture_edit: String = intent.getStringExtra("board_imageURL").toString()
        val board_hex_color_edit: String = intent.getStringExtra("board_hex_color").toString()

        ivProfilePictureEditBoard.load(board_picture_edit)
        etEnterTitleEditBoard.setText(project_name_edit)
        ivColorPickerEditBoard.setBackgroundColor(Color.parseColor(board_hex_color_edit))

        ivBackEditBoard.setOnClickListener{
            finish()
        }

        ivColorPickerEditBoard.setOnClickListener {
            MaterialColorPickerDialog.Builder(this)
                .setTitle("Pick Color")
                .setColorShape(ColorShape.CIRCLE)
                .setColorRes(resources.getIntArray(R.array.boardColorBox))
                .setColorListener { color, colorHex ->
                    ivColorPickerEditBoard.setBackgroundColor(Color.parseColor(colorHex))
                    hexColor = colorHex
                }
                .show()
        }

        ivChecklistEditBoard.setOnClickListener{
            val project_name_new: String = etEnterTitleEditBoard.text.toString()

            if (TextUtils.isEmpty(project_name_new)) {
                etEnterTitleEditBoard.error = getString(R.string.board_title_required)
                return@setOnClickListener
            }
            if (project_name_new.length >= 24) {
                etEnterTitleEditBoard.error = getString(R.string.board_title_too_long)
                return@setOnClickListener
            }
            //check if the image new or not
            if (image_uri != null) {
                updateDataBoard(board_id_edit, project_name_new, image_uri.toString(), hexColor)
            }
            if (image_uri == null) {
                updateDataBoard(board_id_edit, project_name_new, board_picture_edit, hexColor)
            }
            finish()
        }

        btnEditBoardPicture.setOnClickListener{
            openGallery()
        }

    }

    private fun updateDataBoard(board_id: String, project_name: String, board_imageURL : String, hexColor: String) {

        database.collection("boards").document(board_id)
            .update("project_name", project_name, "board_imageURL", board_imageURL, "board_hex_color", hexColor)
            .addOnSuccessListener {
                Toast.makeText(this, "Update board $project_name successfully!", Toast.LENGTH_SHORT)
                Log.d("UPDATE BOARD", "Update board $board_id | $project_name successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("UPDATE BOARD", "Update board $board_id | $project_name failed!", e)
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
            val storageReferenceDelete = storage.getReference("boardpicture/" + project_name_edit)
            storageReferenceDelete.delete()
            val project_name_new = etEnterTitleEditBoard.text.toString()
            storageReference = storage.getReference("boardpicture/" + project_name_new)

            val uploadTask = storageReference.putFile(image_uri!!)
            uploadTask.addOnSuccessListener {
                storageReference.downloadUrl.addOnCompleteListener {
                    imageurl = it.result
                    Log.d("UPLOAD PICTURE", "Upload picture: $imageurl")
                    ivProfilePictureEditBoard.load(imageurl)
                }
            }
            // set gallery image to image view
        }
    }
}