package com.example.project1.retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.R
import com.example.project1.modelclass.Quote

class RecyclerViewAdapter (private val context: Context, private val dataset:List<Quote>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){

        class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
             var id : TextView
             var user_id : TextView
             var title : TextView
             var body : TextView
            init {
                id = itemView.findViewById(R.id.id)
                user_id = itemView.findViewById(R.id.user_id)
                title = itemView.findViewById(R.id.title)
                body = itemView.findViewById(R.id.body)
            }



    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ):  ItemViewHolder {
       val itemView = LayoutInflater.from(context).inflate(R.layout.card_list , parent,false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.id.text = dataset[position].id.toString()
        holder.user_id.text = dataset[position].user_id.toString()
        holder.title.text = dataset[position].title
        holder.body.text = dataset[position].body


        val recyclerquotelist = getItemId(position)
        //holder.bind(recyclerquotelist)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}



