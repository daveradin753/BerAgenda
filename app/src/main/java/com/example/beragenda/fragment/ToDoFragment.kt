package com.example.beragenda.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.adapter.BoardToDoCustomAdapter
import com.example.beragenda.model.DoingCards
import com.example.beragenda.model.ToDoCards

class ToDoFragment : Fragment() {

    private lateinit var rvBoardsToDo: RecyclerView
    private var dataTodoCard: MutableList<ToDoCards> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
        val adapter = BoardToDoCustomAdapter(dataTodoCard)

        rvBoardsToDo = view.findViewById(R.id.rvBoardsToDO)
        rvBoardsToDo.layoutManager = layoutManager
        rvBoardsToDo.adapter = adapter

    }

    private fun addToDoCard() {
        dataTodoCard.add(ToDoCards("Test Doing Card"))
    }

    companion object {

    }
}