package com.dev.newsapp.utils

import androidx.recyclerview.widget.DiffUtil
import com.dev.newsapp.data.model.entity.SourceData

class SourcesDiffCallback(private val oldSourceList: List<SourceData>, private val newSourceList: List<SourceData>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldSourceList.size

    override fun getNewListSize(): Int = newSourceList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSourceList[oldItemPosition].id == newSourceList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldSources = oldSourceList[oldItemPosition]
        val newSources = newSourceList[newItemPosition]
        return oldSources.name == newSources.name && oldSources.language == newSources.language
                && oldSources.country == newSources.country
    }


}