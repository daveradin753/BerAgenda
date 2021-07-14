package com.example.beragenda.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.beragenda.R
import com.example.beragenda.adapter.ViewPagerAdapater
import com.example.beragenda.fragment.DoingFragment
import com.example.beragenda.fragment.DoneFragment
import com.example.beragenda.fragment.ToDoFragment
import com.google.android.material.tabs.TabLayout

private lateinit var viewPager : ViewPager
private lateinit var tabLayout : TabLayout

class BoardCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board_card)

        setUpTabs()
    }

    private fun setUpTabs(){

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapater(supportFragmentManager)
        adapter.addFragment(ToDoFragment()," ToDo")
        adapter.addFragment(DoingFragment(),"Doing")
        adapter.addFragment(DoneFragment(),"Done")
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

//        tabLayout.getTabAt(0)!!.setText("To Do")
//        tabLayout.getTabAt(1)!!.setText("Doing")
//        tabLayout.getTabAt(2)!!.setText("Done")
    }
}