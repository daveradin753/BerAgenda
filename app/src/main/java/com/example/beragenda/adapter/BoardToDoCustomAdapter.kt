package com.example.beragenda.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.activity.EditContentBoardActivity
import com.example.beragenda.model.TasksBoard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoardToDoCustomAdapter (private val dataset: MutableList<TasksBoard>,
                              private val board_id: String) :
        RecyclerView.Adapter<BoardToDoCustomAdapter.ViewHolder>() {

    private lateinit var database: FirebaseFirestore

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardTodoContext: TextView = view.findViewById(R.id.tvCardTodoContext)
        val btnEditTodoCard: ImageView = view.findViewById(R.id.btnEditTodoCard)
        val btnDeleteTodoCard: ImageView = view.findViewById(R.id.btnDeleteTodoCard)
        val btnForwardTodoCard: ImageView = view.findViewById(R.id.btnForwardTodoCard)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recylerview_todo_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCardTodoContext.text = dataset[position].task_name
        holder.btnDeleteTodoCard.setOnClickListener {
            deleteToDoCard(dataset[position].task_id)
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnForwardTodoCard.setOnClickListener {
            database = Firebase.firestore
            database.collection("boards").document(board_id).collection("tasks").document(dataset[position].task_id)
                .update("type", 1)
                .addOnSuccessListener {
                    Log.d("TO DO CARD", "Update todo card completed!")
                }
                .addOnFailureListener { e ->
                    Log.e("TO DO CARD", "Update todo card failed!", e)
                }
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnEditTodoCard.setOnClickListener {
            val intent = Intent(holder.itemView.context, EditContentBoardActivity::class.java)
            intent.putExtra("board_id", board_id)
            intent.putExtra("task_id", dataset[position].task_id)
            intent.putExtra("task_name", dataset[position].task_name)
            intent.putExtra("type", dataset[position].type)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    private fun deleteToDoCard(task_id: String) {
        database = Firebase.firestore
        database.collection("boards").document(board_id).collection("tasks").document(task_id)
            .delete()
            .addOnSuccessListener {
                Log.d("TO DO CARD", "Delete todo card completed!")
            }
            .addOnFailureListener { e ->
                Log.e("TO DO CARD", "Delete todo card failed!", e)
            }
    }
}