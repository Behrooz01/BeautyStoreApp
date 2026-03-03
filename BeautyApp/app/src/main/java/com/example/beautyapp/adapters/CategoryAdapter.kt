package com.example.beautyapp.adapters

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.example.beautyapp.R

import com.example.beautyapp.databinding.ViewholderCategoryBinding
import com.example.beautyapp.domain.CategoryModel


class CategoryAdapter(val items:List<CategoryModel>): RecyclerView.Adapter
<CategoryAdapter.Viewholder>(){
    var selectedPosition = -1
    var selectedLastPosition = -1
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryAdapter.Viewholder {
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.Viewholder, position: Int) {
        val item = items[position]
        holder.binding.titleTxt.text = item.title

        if(selectedPosition == position){
            holder.binding.titleTxt.setBackgroundResource(R.drawable.purple_bg)
            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.darkGrey
                )
            )
        }else {
            holder.binding.titleTxt.setBackgroundResource(R.drawable.white_bg)
            holder.binding.titleTxt.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )
        }

        holder.binding.root.setOnClickListener {
            val position = position
            if(position != RecyclerView.NO_POSITION) {
                notifyItemChanged(selectedLastPosition)
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount(): Int = items.size

    class Viewholder(val binding: ViewholderCategoryBinding):
    RecyclerView.ViewHolder(binding.root){

    }
}