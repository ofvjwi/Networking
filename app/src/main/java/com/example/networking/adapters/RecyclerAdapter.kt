package com.example.networking.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.networking.R
import com.example.networking.activities.MainActivity
import com.example.networking.models.Poster

class RecyclerAdapter(private val activity: MainActivity, private val items: ArrayList<Poster>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val poster = items[position]

        if (holder is MyViewHolder) {
            (holder as MyViewHolder).linearLayout.setOnClickListener {
                activity.dialogPoster(poster)
            }
            (holder as MyViewHolder).titleTextView.text = poster.title
            (holder as MyViewHolder).descriptionTextView.text = poster.body
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.text_view_title)
        val descriptionTextView: TextView = itemView.findViewById(R.id.text_view_description)
        val linearLayout: LinearLayout = itemView.findViewById(R.id.linear_layout)
    }
}

