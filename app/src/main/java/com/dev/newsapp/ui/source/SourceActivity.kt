package com.dev.newsapp.ui.source

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.newsapp.R
import com.dev.newsapp.data.Resource
import com.dev.newsapp.databinding.ActivitySourceBinding
import com.dev.newsapp.ui.article.ArticleActivity
import com.dev.newsapp.utils.capitalizeFirstChar
import com.dev.newsapp.utils.checkInternet
import com.dev.newsapp.viewmodel.SourceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SourceActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySourceBinding
    private lateinit var adapter: SourceAdapter
    private val sourceViewModel: SourceViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySourceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val category = intent.getStringExtra("category")

        adapter = SourceAdapter{sourceId,sourceName ->
            val checkInternet = checkInternet(this)

            if(checkInternet){
                val intent = Intent(this@SourceActivity,ArticleActivity::class.java)
                intent.putExtra("id",sourceId)
                intent.putExtra("source",sourceName)
                startActivity(intent)
            }else{
                Toast.makeText(this, "please check your connection", Toast.LENGTH_SHORT).show()
            }

        }

        with(binding){
            btnBack.setOnClickListener { finish() }
            txvCategory.text = category?.capitalizeFirstChar()

            rvSource.layoutManager = LinearLayoutManager(this@SourceActivity)
            rvSource.adapter = adapter

            category?.let { getAllUsers(it) }

            edtSearch.addTextChangedListener { s ->
                val isSearching = if(s.toString().isNotEmpty()){
                    lifecycleScope.launch {
                        sourceViewModel.searchSource(s.toString()).observe(this@SourceActivity){ resources ->
                            when(resources){
                                is Resource.Loading -> {
                                    Toast.makeText(this@SourceActivity,"Loading..",Toast.LENGTH_SHORT).show()
                                }
                                is Resource.Success -> {
                                    resources.data?.let { adapter.setListSource(it) }
                                }
                                is Resource.Error ->{
                                    Toast.makeText(this@SourceActivity,"Error retrieve data",Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                    true
                }else{
                    category?.let { getAllUsers(it) }
                    false
                }
                checkEndButton(isSearching)
            }
        }
    }

    private fun checkEndButton(isSearching: Boolean) {
        if(isSearching){
            binding.btnClear.setBackgroundResource(R.drawable.baseline_clear_24)
            binding.btnClear.setOnClickListener {
                binding.edtSearch.setText("")
            }
        }else{
            binding.btnClear.setBackgroundResource(R.drawable.baseline_search_24)
        }
    }

    private fun getAllUsers(category: String) {
        lifecycleScope.launch {
            sourceViewModel.sources.observe(this@SourceActivity){ resources ->
                when(resources){
                    is Resource.Loading -> {
                        Toast.makeText(this@SourceActivity,"Loading..",Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Success -> {
                        resources.data?.let { adapter.setListSource(it) }
                    }
                    is Resource.Error ->{
                        Toast.makeText(this@SourceActivity,"Error retrieve data",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            sourceViewModel.getSources(category)

        }
    }

    override fun onStart() {
        super.onStart()
        binding.btnClear.setBackgroundResource(R.drawable.baseline_search_24)
    }
}