package com.example.beragenda.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.beragenda.R
import com.example.beragenda.adapter.ViewPagerAdapter
import com.example.beragenda.databinding.ActivityBoardCardBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoardCardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardCardBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var tvSelectedBoardTitle: TextView
//    private lateinit var btnBoardCardDialog: ImageView
    private lateinit var database : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = Firebase.firestore

        tvSelectedBoardTitle = findViewById(R.id.tvSelectedBoardTitle)
//        btnBoardCardDialog = findViewById(R.id.btnBoardCardDialog)

        val project_name = intent.getStringExtra("project_name")
        val board_id = intent.getStringExtra("board_id")
        tvSelectedBoardTitle.setText(project_name)

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, board_id.toString())

        with(binding){
            viewPager.adapter = viewPagerAdapter

            TabLayoutMediator(tabLayout, viewPager){ tab, position ->
                when(position){
                    0 -> tab.text = "To Do"
                    1 -> tab.text = "Doing"
                    2 -> tab.text = "Done"
                }
            }.attach()
        }


        val backBoardCard = findViewById<ImageView>(R.id.backBoardCard)
        backBoardCard.setOnClickListener {
            finish()
        }

        val bundle = Bundle()
        bundle.putString("board_id", board_id)
        bundle.putString("project_name", project_name)


    }
}