package com.example.beragenda.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.activity.AddContentBoardActivity
import com.example.beragenda.adapter.BoardToDoCustomAdapter
import com.example.beragenda.model.TasksBoard
import com.example.beragenda.model.ToDoCards
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class ToDoFragment(val board_id: String) : Fragment() {

    private lateinit var rvBoardsToDo: RecyclerView
    private lateinit var btnAddCard: View
    private lateinit var database: FirebaseFirestore
    private lateinit var bundle: Bundle
    private var dataToDoCard: MutableList<TasksBoard> = ArrayList()

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

        rvBoardsToDo = view.findViewById(R.id.rvBoardsToDO)
        btnAddCard = view.findViewById(R.id.btnAddCard)
//        val board_id = arguments?.getString("board_id")
//        val project_name = arguments?.getString("project_name")
//        val bundle = arguments?.getBundle("todoBundle")
//        val board_id = bundle?.getString("board_id")
//        val project_name = bundle?.getString("project_name")
        Log.d("GET TODO CARD", "Read todo card on $board_id successed!")
//        Log.d("GET TODO CARD", "Read todo card on $project_name successed!")

//        Toast.makeText(this.context, board_id, Toast.LENGTH_SHORT).show()

        database = Firebase.firestore
        getToDoCard(board_id)

        btnAddCard.setOnClickListener {
            val intent = Intent(this.context, AddContentBoardActivity::class.java)
            startActivity(intent)
        }

    }

    private fun getToDoCard(board_id: String) {
        dataToDoCard.clear()
        database.collection("boards").document(board_id).collection("tasks")
            .whereEqualTo("type", 0)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val task_id = document.getString("task_id").toString()
                    val task_name = document.getString("task_name").toString()
                    val type: Int = document.getLong("type")!!.toInt()
                    dataToDoCard.add(TasksBoard(task_id, task_name, type))

                    val layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
                    val adapter = BoardToDoCustomAdapter(dataToDoCard, board_id)

                    rvBoardsToDo.layoutManager = layoutManager
                    rvBoardsToDo.setHasFixedSize(true)
                    rvBoardsToDo.adapter = adapter

                }
            }

    }

    companion object {

    }
}