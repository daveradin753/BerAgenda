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
import com.example.beragenda.adapter.BoardToDoCustomAdapter
import com.example.beragenda.model.DoingCards
import com.example.beragenda.model.TasksBoard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DoneFragment(val board_id: String) : Fragment() {

    private lateinit var rvBoardsDone: RecyclerView
    private var dataDoneCards: MutableList<TasksBoard> = ArrayList()
    private lateinit var database: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_done, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvBoardsDone = view.findViewById(R.id.rvBoardsDone)
        database = Firebase.firestore

        getDoneCard(board_id)


    }

    private fun getDoneCard(board_id: String) {
        dataDoneCards.clear()
        database.collection("boards").document(board_id).collection("tasks")
            .whereEqualTo("type", 2)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val task_id = document.getString("task_id").toString()
                    val task_name = document.getString("task_name").toString()
                    val type: Int = document.getLong("type")!!.toInt()
                    dataDoneCards.add(TasksBoard(task_id, task_name, type))

                    val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                    val adapter = BoardDoneCustomAdapter(dataDoneCards, board_id)

                    rvBoardsDone.layoutManager = layoutManager
                    rvBoardsDone.setHasFixedSize(true)
                    rvBoardsDone.adapter = adapter

                }
            }

    }
    companion object {

    }
}