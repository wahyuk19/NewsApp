package com.dev.newsapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.dev.newsapp.data.model.Categories

class CategoryDiffCallback(private val oldCatList: List<Categories>, private val newCatList: List<Categories>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldCatList.size

    override fun getNewListSize(): Int = newCatList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCatList[oldItemPosition].id == newCatList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCat = oldCatList[oldItemPosition]
        val newCat = newCatList[newItemPosition]
        return oldCat.title == newCat.title
    }
}