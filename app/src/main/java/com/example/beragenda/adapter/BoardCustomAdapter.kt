package com.example.beragenda.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.activity.CardActivity
import com.example.beragenda.model.Boards

class BoardCustomAdapter(private val dataset: List<Boards>) :
    RecyclerView.Adapter<BoardCustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivBoardsImage: ImageView
        val tvBoardsTitle: TextView
        val btnColorBoard: Button
        val btnDeleteBoard: Button
        val btnEditBoard: Button

        init {
            ivBoardsImage = view.findViewById(R.id.ivBoardsImage)
            tvBoardsTitle = view.findViewById(R.id.tvBoardsTitle)
            btnColorBoard = view.findViewById(R.id.btnColorBoard)
            btnDeleteBoard = view.findViewById(R.id.btnDeleteBoard)
            btnEditBoard = view.findViewById(R.id.btnEditBoard)
            view.setOnClickListener { 
                val intent = Intent(view.context, CardActivity::class.java)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerview_board_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvBoardsTitle.text = dataset[position].title
//        holder.ivBoardsImage
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}