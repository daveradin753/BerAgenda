package com.example.beragenda.activity

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.viewpager.widget.ViewPager
import com.example.beragenda.R
import com.example.beragenda.adapter.ViewPagerAdapter
import com.example.beragenda.databinding.ActivityBoardCardBinding
import com.example.beragenda.fragment.DoingFragment
import com.example.beragenda.fragment.DoneFragment
import com.example.beragenda.fragment.ToDoFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text

class BoardCardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityBoardCardBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private lateinit var tvSelectedBoardTitle: TextView
    private lateinit var btnBoardCardDialog: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardCardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvSelectedBoardTitle = findViewById(R.id.tvSelectedBoardTitle)
        btnBoardCardDialog = findViewById(R.id.btnBoardCardDialog)

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

        btnBoardCardDialog.setOnClickListener {
            val popupMenu: PopupMenu = PopupMenu(this, btnBoardCardDialog)

            popupMenu.inflate(R.menu.board_card_menu)
            popupMenu.setOnMenuItemClickListener { items ->
                when(items.itemId) {
                    R.id.delete_all_card -> {
                        Toast.makeText(this, "All card has been deleted!", Toast.LENGTH_SHORT).show()
                    }
                    R.id.members -> {
                        Toast.makeText(this, "Open all members on this board!", Toast.LENGTH_SHORT).show()
                    }

                }
                true
            }
            popupMenu.show()
        }

        val bundle = Bundle()
        bundle.putString("board_id", board_id)
        bundle.putString("project_name", project_name)


    }
}