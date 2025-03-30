package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rai.blink.model.ScannedItem

class ItemsAdapter(private val items: List<ScannedItem>) : RecyclerView.Adapter<ItemsAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineItemId: TextView = itemView.findViewById(R.id.lineItemId)
        val data: TextView = itemView.findViewById(R.id.data)
        val sku: TextView = itemView.findViewById(R.id.sku)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.lineItemId.text = item.lineItemId.toString()
        holder.data.text = item.data
        holder.sku.text = item.extras.sku
    }

    override fun getItemCount(): Int = items.size
}