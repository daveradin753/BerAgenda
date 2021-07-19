package com.example.beragenda.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.adapter.BoardDoingCustomAdapter
import com.example.beragenda.model.DoingCards

class DoingFragment : Fragment() {

    private lateinit var rvBoardsDoing: RecyclerView
    private var dataDoingCards: MutableList<DoingCards> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDoingCard()

        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = BoardDoingCustomAdapter(dataDoingCards)

        rvBoardsDoing = view.findViewById(R.id.rvBoardsDoing)
        rvBoardsDoing.layoutManager = layoutManager
        rvBoardsDoing.adapter = adapter
    }

    private fun addDoingCard () {
        dataDoingCards.add(DoingCards("This is test card!"))
    }

    companion object {

    }
}