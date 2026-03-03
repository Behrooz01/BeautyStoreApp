package com.example.beautyapp.mainViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.beautyapp.domain.CategoryModel
import com.example.beautyapp.domain.ItemsModel
import com.example.beautyapp.domain.Slider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.MutableData
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

class MainViewModel: ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private val _category = MutableLiveData<List<CategoryModel>>()

    val category: LiveData<List<CategoryModel>> = _category
    private val _banner = MutableLiveData<List<Slider>>()
    val banner: LiveData<List<Slider>> = _banner

    private val _popular = MutableLiveData<List<ItemsModel>>()
    val popular: LiveData<List<ItemsModel>>
    = _popular

    fun loadCategory() {

        var Ref = firebaseDatabase.getReference("Category")
            Ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<CategoryModel>()

                    for(child in snapshot.children) {
                        val model = child.getValue(CategoryModel::class.java)
                        if(model != null) {
                            list.add(model)
                        }
                    }
                    _category.value = list
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    fun loadBanner() {

        var Ref = firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = mutableListOf<Slider>()

                for(child in snapshot.children) {
                    val model = child.getValue(Slider::class.java)
                    if(model != null) {
                        list.add(model)
                    }
                }
                _banner.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    fun loadPopular() {
        val ref = firebaseDatabase.getReference("Items")

        val query: Query = ref.orderByChild("showRecommended").equalTo(true)
        query.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<ItemsModel>()

                for(childSnapshot in snapshot.children) {
                    val list = childSnapshot.getValue(ItemsModel::class.java)
                    if(list != null){
                        lists.add(list)
                    }
                }
                _popular.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
