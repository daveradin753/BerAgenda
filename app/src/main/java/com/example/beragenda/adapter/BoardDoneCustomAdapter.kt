package com.example.beragenda.adapter

import android.content.Intent
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.activity.EditContentBoardActivity
import com.example.beragenda.model.Boards
import com.example.beragenda.model.DoneCards
import com.example.beragenda.model.TasksBoard
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoardDoneCustomAdapter(private val dataset: MutableList<TasksBoard>,
private val board_id: String) :
    RecyclerView.Adapter<BoardDoneCustomAdapter.ViewHolder>() {

    private lateinit var database: FirebaseFirestore

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardDoneContext: TextView
        val btnEditDoneCard: ImageView
        val btnDeleteDoneCard: ImageView
        val btnBackDoneCard: ImageView

        init {
            tvCardDoneContext = view.findViewById(R.id.tvCardDoneContext)
            btnEditDoneCard = view.findViewById(R.id.btnEditDoneCard)
            btnDeleteDoneCard = view.findViewById(R.id.btnDeleteDoneCard)
            btnBackDoneCard = view.findViewById(R.id.btnBackDoneCard)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerview_done_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        database = Firebase.firestore
        holder.tvCardDoneContext.text = dataset[position].task_name
        holder.btnBackDoneCard.setOnClickListener {
            database.collection("boards").document(board_id).collection("tasks").document(dataset[position].task_id)
                .update("type", 1)
                .addOnSuccessListener {
                    Log.d("DONE CARD", "Update done card completed!")
                }
                .addOnFailureListener { e ->
                    Log.e("DONE CARD", "Update done card failed!", e)
                }
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnDeleteDoneCard.setOnClickListener {
            deleteDoneCard(dataset[position].task_id)
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.btnEditDoneCard.setOnClickListener {
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

    private fun deleteDoneCard(task_id: String) {
        database.collection("boards").document(board_id).collection("tasks").document(task_id)
            .delete()
            .addOnSuccessListener {
                Log.d("DONE CARD", "Delete done card completed!")
            }
            .addOnFailureListener { e ->
                Log.e("DONE CARD", "Delete done card failed!", e)
            }
    }
}