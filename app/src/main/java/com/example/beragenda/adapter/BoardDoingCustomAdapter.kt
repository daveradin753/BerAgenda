package com.example.beragenda.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.model.Boards
import com.example.beragenda.model.DoingCards
import com.example.beragenda.model.TasksBoard

class BoardDoingCustomAdapter(private val dataset: MutableList<TasksBoard>) :
        RecyclerView.Adapter<BoardDoingCustomAdapter.ViewHolder>() {

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
        holder.tvCardDoingContext.text = dataset[position].task_name
//        holder.ivBoardsImage
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}