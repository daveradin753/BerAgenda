package com.example.beragenda.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.activity.AddBoardActivity
import com.example.beragenda.activity.BoardCardActivity
import com.example.beragenda.fragment.ToDoFragment
import com.example.beragenda.model.Boards
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class BoardCustomAdapter(private val dataset: MutableList<Boards>) :
    RecyclerView.Adapter<BoardCustomAdapter.ViewHolder>() {

    private lateinit var database: FirebaseFirestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerview_board_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val board_id = dataset[position].board_id
        val project_name = dataset[position].project_name
        holder.tvBoardsTitle.text = dataset[position].project_name
        holder.btnDeleteBoard.setOnClickListener {
            deleteDataBoard(dataset[position].board_id)
            dataset.removeAt(position)
            notifyDataSetChanged()
        }
        holder.itemView.setOnClickListener {
            //            val user_id = dataset[position].user_id
////            intent.putStringArrayListExtra("user_id", user_id)

//            val bundle = Bundle()
//            val toDoFragment = ToDoFragment()
//            bundle.putString("board_id", board_id)
//            bundle.putString("project_name", project_name)
//            toDoFragment.arguments = bundle
//            Log.d("PASS DATA BOARD", "data board -> $board_id")

            val intent = Intent(holder.itemView.context, BoardCardActivity::class.java)
            intent.putExtra("board_id", board_id)
            intent.putExtra("project_name", project_name)
            holder.itemView.context.startActivity(intent)

        }
        holder.btnEditBoard.setOnClickListener {
            val intent = Intent(holder.itemView.context, AddBoardActivity::class.java)
            intent.putExtra("board_id", board_id)
            intent.putExtra("project_name", project_name)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBoardsImage: ImageView
        val tvBoardsTitle: TextView
        val btnColorBoard: Button
        val btnDeleteBoard: Button
        val btnEditBoard: Button
        val cvBoard: CardView

        init {
            ivBoardsImage = view.findViewById(R.id.ivBoardsImage)
            tvBoardsTitle = view.findViewById(R.id.tvBoardsTitle)
            btnColorBoard = view.findViewById(R.id.btnColorBoard)
            btnDeleteBoard = view.findViewById(R.id.btnDeleteBoard)
            btnEditBoard = view.findViewById(R.id.btnEditBoard)
            cvBoard = view.findViewById(R.id.cvBoard)

//            view.setOnClickListener {
//                val intent = Intent(view.context, BoardCardActivity::class.java)
//                view.context.startActivity(intent)
//            }
        }
    }

    private fun deleteDataBoard(board_id: String) {
        database = Firebase.firestore
        database.collection("boards").document(board_id)
            .delete()
            .addOnSuccessListener { Log.d("DATA BOARD", "Document $board_id has been deleted!") }
            .addOnFailureListener { e -> Log.e("DATA BOARD", "Error deleting document!", e) }
    }
}