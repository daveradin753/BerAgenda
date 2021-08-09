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
import com.example.beragenda.model.TasksBoard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DoingFragment(val board_id: String) : Fragment() {

    private lateinit var rvBoardsDoing: RecyclerView
    private var dataDoingCards: MutableList<TasksBoard> = ArrayList()
    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBoardsDoing = view.findViewById(R.id.rvBoardsDoing)
        database = Firebase.firestore

//        getDoingCard(board_id)


    }

    override fun onResume() {
        super.onResume()
        getDoingCard(board_id)
    }

    private fun getDoingCard(board_id: String) {
        dataDoingCards.clear()
        database.collection("boards").document(board_id).collection("tasks")
            .whereEqualTo("type", 1)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val task_id = document.getString("task_id").toString()
                    val task_name = document.getString("task_name").toString()
                    val type: Int = document.getLong("type")!!.toInt()
                    dataDoingCards.add(TasksBoard(task_id, task_name, type))

                    val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                    val adapter = BoardDoingCustomAdapter(dataDoingCards, board_id)

                    rvBoardsDoing.layoutManager = layoutManager
                    rvBoardsDoing.setHasFixedSize(true)
                    rvBoardsDoing.adapter = adapter

                }
            }

    }

    companion object {

    }
}