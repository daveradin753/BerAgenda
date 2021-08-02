package com.example.beragenda.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.beragenda.fragment.DoingFragment
import com.example.beragenda.fragment.DoneFragment
import com.example.beragenda.fragment.ToDoFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifeCycle : Lifecycle,val board_id: String) : FragmentStateAdapter(fragmentManager, lifeCycle) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when(position){
            0 -> fragment = ToDoFragment(board_id)
            1 -> fragment = DoingFragment()
            2 -> fragment = DoneFragment()
        }
        return fragment
    }
}