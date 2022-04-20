package com.example.project1.retrofit

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project1.databinding.RecyclerListBinding
import com.example.project1.modelclass.Quote

class RecyclerViewAdapter (private val context: Context,private val dataset:List<Quote>)
    : RecyclerView.Adapter<RecyclerViewAdapter.ItemViewHolder>(){
    class ItemViewHolder(private val view: RecyclerListBinding) : RecyclerView.ViewHolder(view.root){
fun bind(quote: Quote){
   /* view.viewmodel = quote
    view.executePendingBindings()*/
}


    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        return ItemViewHolder(RecyclerListBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
     val recyclerquotelist = getItemId(position)
        //holder.bind(recyclerquotelist)

    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}


