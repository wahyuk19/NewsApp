package com.dev.newsapp.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.newsapp.data.model.Categories
import com.dev.newsapp.databinding.ItemCategoriesBinding
import com.dev.newsapp.utils.CategoryDiffCallback

class CategoryAdapter(val callback: (category: String) -> Unit): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val listCategory = ArrayList<Categories>()

    fun setListCategories(listCat: List<Categories>){
        val diffCallback = CategoryDiffCallback(listCategory,listCat)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listCategory.clear()
        listCategory.addAll(listCat)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoriesBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listCategory.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(listCategory[position])
    }

    inner class CategoryViewHolder(private val binding: ItemCategoriesBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Categories){
            with(binding){
                txvCategories.text = item.title

                root.setOnClickListener {
                    callback(item.title.lowercase())
                }
            }
        }
    }
}