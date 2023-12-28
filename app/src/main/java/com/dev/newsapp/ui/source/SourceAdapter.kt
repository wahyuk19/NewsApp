package com.dev.newsapp.ui.source

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.newsapp.data.model.entity.SourceData
import com.dev.newsapp.databinding.ItemSourceBinding
import com.dev.newsapp.utils.SourcesDiffCallback

class SourceAdapter(val callback: (id: String,source: String) -> Unit): RecyclerView.Adapter<SourceAdapter.SourceViewHolder>() {

    private val listSource =  ArrayList<SourceData>()

    fun setListSource(lstSource: List<SourceData>){
        val diffCallback = SourcesDiffCallback(listSource,lstSource)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        listSource.clear()
        listSource.addAll(lstSource)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val binding = ItemSourceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SourceViewHolder(binding)
    }

    override fun getItemCount(): Int = listSource.size

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        holder.bind(listSource[position])
    }

    inner class SourceViewHolder(private val binding: ItemSourceBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: SourceData){
            with(binding){
                txvSourceName.text = item.name
                txvCountry.text = item.country

                root.setOnClickListener {
                    callback(item.id,item.name)
                }
            }
        }
    }
}