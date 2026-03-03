package com.example.beautyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beautyapp.databinding.ViewholderPopulerBinding
import com.example.beautyapp.domain.ItemsModel

class PopularAdapter(val items:List<ItemsModel>)
    : RecyclerView.Adapter<PopularAdapter.ViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ViewholderPopulerBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = items[position]
        with(holder.binding) {
            popTitle.text = item.title
            popPrice.text = "$${item.price}"
            popRating.text = item.rating.toString()

            Glide.with(holder.itemView.context).
                    load(item.picUrl[0])
                .into(popImg)

        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(val binding: ViewholderPopulerBinding):
    RecyclerView.ViewHolder(binding.root){

    }

}