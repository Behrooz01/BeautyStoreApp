package com.example.beautyapp.adapters


import android.content.Context
import android.text.Layout
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.beautyapp.databinding.SliderItemContainerBinding
import com.example.beautyapp.domain.Slider

class SliderAdapter(private var sliderItems: List<Slider>,
    private val viewPager: ViewPager2): RecyclerView.Adapter<SliderAdapter.SliderViewHolder>() {
    private val runnable = Runnable {
        sliderItems = sliderItems
        notifyDataSetChanged()
    }
    private lateinit var context: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SliderViewHolder {
        val binding = SliderItemContainerBinding.inflate(
            LayoutInflater.from(parent.context), parent , false
        )
        context = parent.context
         return SliderViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: SliderViewHolder,
        position: Int
    ) {
       holder.setImage(sliderItems[position],context)
        if (position == sliderItems.lastIndex-1) {
            viewPager.post { runnable }
        }
    }

    override fun getItemCount(): Int =
        sliderItems.size

    class SliderViewHolder(private val binding: SliderItemContainerBinding):
        RecyclerView.ViewHolder(binding.root){
        fun setImage(sliderModel: Slider, context: Context) {
            binding.apply {
                Glide.with(context).
                        load(sliderModel.url)
                    .into(binding.imageSlide)
            }
        }

    }

}