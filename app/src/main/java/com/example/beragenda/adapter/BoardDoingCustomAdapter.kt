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

class BoardDoingCustomAdapter(private val dataset: MutableList<TasksBoard>,
private val board_id: String) :
        RecyclerView.Adapter<BoardDoingCustomAdapter.ViewHolder>() {

    private lateinit var database: FirebaseFirestore

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardDoingContext: TextView
        val btnEditDoingCard: ImageView
        val btnDeleteDoingCard: ImageView
        val btnForwardDoingCard: ImageView
        val btnBackDoingCard: ImageView

        init {
            tvCardDoingContext = view.findViewById(R.id.tvCardDoingContext)
            btnEditDoingCard = view.findViewById(R.id.btnEditDoingCard)
            btnDeleteDoingCard = view.findViewById(R.id.btnDeleteDoingCard)
            btnForwardDoingCard = view.findViewById(R.id.btnForwardDoingCard)
            btnBackDoingCard = view.findViewById(R.id.btnBackDoingCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recylerview_doing_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        database = Firebase.firestore
        holder.tvCardDoingContext.text = dataset[position].task_name
        holder.btnBackDoingCard.setOnClickListener {
            database.collection("boards").document(board_id).collection("tasks").document(dataset[position].task_id)
                .update("type", 0)
                .addOnSuccessListener {
                    Log.d("DOING CARD", "Update doing card completed!")
                }
                .addOnFailureListener { e ->
                    Log.e("DOING CARD", "Update doing card failed!", e)
                }
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnDeleteDoingCard.setOnClickListener {
            deleteDoingCard(dataset[position].task_id)
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnForwardDoingCard.setOnClickListener {
            database.collection("boards").document(board_id).collection("tasks").document(dataset[position].task_id)
                .update("type", 2)
                .addOnSuccessListener {
                    Log.d("DOING CARD", "Update doing card completed!")
                }
                .addOnFailureListener { e ->
                    Log.e("DOING CARD", "Update doing card failed!", e)
                }
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnEditDoingCard.setOnClickListener {
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

    private fun deleteDoingCard(task_id: String) {
        database.collection("boards").document(board_id).collection("tasks").document(task_id)
            .delete()
            .addOnSuccessListener {
                Log.d("DOING CARD", "Delete doing card completed!")
            }
            .addOnFailureListener { e ->
                Log.e("DOING CARD", "Delete doing card failed!", e)
            }
    }
}