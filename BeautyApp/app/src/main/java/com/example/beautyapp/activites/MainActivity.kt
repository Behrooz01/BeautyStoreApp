package com.example.beautyapp.activites

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.beautyapp.R
import com.example.beautyapp.adapters.CategoryAdapter
import com.example.beautyapp.adapters.PopularAdapter
import com.example.beautyapp.adapters.SliderAdapter
import com.example.beautyapp.databinding.ActivityMainBinding
import com.example.beautyapp.domain.Slider
import com.example.beautyapp.mainViewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val viewModel = MainViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        //Code

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setEdges()

        initCategory()

        initBanner()
        initPopular()

    }

    private fun initPopular() {
        binding.apply {
            papularProgress.visibility = View.VISIBLE
            viewModel.popular.observe(this@MainActivity,
                Observer{
                    recyclerViewPopulor.layoutManager =
                        LinearLayoutManager(this@MainActivity,
                            LinearLayoutManager.HORIZONTAL,false)
                    recyclerViewPopulor.adapter = PopularAdapter(it)
                    papularProgress.visibility = View.GONE
                })

        }
        viewModel.loadPopular()
    }

    private fun initBanner() {
        binding.sliderProgressBar.visibility = View.VISIBLE

        viewModel.banner.observe(this, Observer{
            binding.sliderProgressBar.visibility  = View.GONE
            banners(it)
        })
        viewModel.loadBanner()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility = View.VISIBLE
        viewModel.category.observe(this, Observer{
            binding.progressBarCategory.visibility = View.GONE
            binding.viewCategory.adapter = CategoryAdapter(it)
            binding.viewCategory.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false)
        })
        viewModel.loadCategory()
    }

    private fun banners(image: List<Slider>) {
        binding.apply{
            viewPager2.adapter = SliderAdapter(image, viewPager2)
            viewPager2.clipToPadding = false
            viewPager2.clipChildren = false
            viewPager2.offscreenPageLimit = 2
            viewPager2.getChildAt(0).overScrollMode = View.OVER_SCROLL_NEVER

            val compositePageTransformer = CompositePageTransformer().apply {
                addTransformer(MarginPageTransformer(40))

            }
            viewPager2.setPageTransformer (compositePageTransformer)

            if(image.size> 1 ) {
                dotIndicator.visibility = View.VISIBLE
                dotIndicator.attachTo(viewPager2)
            }
        }
    }

    fun setEdges()
    {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}