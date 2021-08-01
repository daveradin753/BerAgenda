package com.example.beragenda.activity

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.example.beragenda.R
import com.example.beragenda.adapter.ViewPagerAdapter
import com.example.beragenda.databinding.ActivityBoardCardBinding
import com.example.beragenda.fragment.DoingFragment
import com.example.beragenda.fragment.DoneFragment
import com.example.beragenda.fragment.ToDoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text

class BoardCardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardCardBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var tvSelectedBoardTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvSelectedBoardTitle = findViewById(R.id.tvSelectedBoardTitle)
        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)

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
        val project_name = intent.getStringExtra("project_name")
        val board_id = intent.getStringExtra("board_id")
        tvSelectedBoardTitle.setText(project_name)

        val backBoardCard = findViewById<ImageView>(R.id.backBoardCard)
        backBoardCard.setOnClickListener {
            finish()
        }

        val bundle = Bundle()
        bundle.putString("board_id", board_id)
        bundle.putString("project_name", project_name)


    }
}