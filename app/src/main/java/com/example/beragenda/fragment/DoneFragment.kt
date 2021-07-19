package com.example.beragenda.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.adapter.BoardDoneCustomAdapter
import com.example.beragenda.model.DoneCards

class DoneFragment : Fragment() {

    private lateinit var rvBoardsDone: RecyclerView
    private var dataDoneCards: MutableList<DoneCards> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addDoneCard()

        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = BoardDoneCustomAdapter(dataDoneCards)

        rvBoardsDone = view.findViewById(R.id.rvBoardsDone)
        rvBoardsDone.layoutManager = layoutManager
        rvBoardsDone.adapter = adapter
    }

    private fun addDoneCard () {
        dataDoneCards.add(DoneCards("This is test card!"))
    }

    companion object {

    }
}