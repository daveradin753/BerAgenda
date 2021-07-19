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
import com.example.beragenda.model.DoneCards

class BoardDoneCustomAdapter(private val dataset: List<DoneCards>) :
    RecyclerView.Adapter<BoardDoneCustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardDoneContext: TextView
        val btnEditDoneCard: ImageView
        val btnDeleteDoneCard: ImageView
        val btnBackDoneCard: ImageView

        init {
            tvCardDoneContext = view.findViewById(R.id.tvCardDoneContext)
            btnEditDoneCard = view.findViewById(R.id.btnEditBoard)
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

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}