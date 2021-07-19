package com.example.beragenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beragenda.R
import com.example.beragenda.model.DoingCards
import com.example.beragenda.model.ToDoCards
import org.w3c.dom.Text

class BoardToDoCustomAdapter (private val dataset: List<ToDoCards>) :
        RecyclerView.Adapter<BoardToDoCustomAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCardTodoContext: TextView
        val btnEditTodoCard: ImageView
        val btnDeleteTodoCard: ImageView
        val btnForwardTodoCard: ImageView

        init {
            tvCardTodoContext = view.findViewById(R.id.tvCardTodoContext)
            btnEditTodoCard = view.findViewById(R.id.btnEditTodoCard)
            btnDeleteTodoCard = view.findViewById(R.id.btnDeleteTodoCard)
            btnForwardTodoCard = view.findViewById(R.id.btnForwardTodoCard)

            btnDeleteTodoCard.setOnClickListener {

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recylerview_todo_card, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvCardTodoContext.text = dataset[position].context
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}