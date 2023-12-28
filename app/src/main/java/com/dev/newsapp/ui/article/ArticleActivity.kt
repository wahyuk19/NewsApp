package com.dev.newsapp.ui.article

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
import com.dev.newsapp.databinding.ActivityArticleBinding
import com.dev.newsapp.ui.webview.WebViewActivity
import com.dev.newsapp.viewmodel.ArticleViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var adapter: ArticleAdapter
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var sourceId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sourceId = intent.getStringExtra("id").toString()
        val sourceName = intent.getStringExtra("source")

        adapter = ArticleAdapter{ url ->
            val intent = Intent(this@ArticleActivity,WebViewActivity::class.java)
            intent.putExtra("url",url)
            startActivity(intent)
        }

        with(binding){
            btnBack.setOnClickListener { finish() }
            txvSource.text = sourceName

            rvArticle.layoutManager = LinearLayoutManager(this@ArticleActivity)
            rvArticle.adapter = adapter

            getArticles()

            edtSearch.addTextChangedListener { s ->
                val isSearching = if(s.toString().isNotEmpty()){
                    lifecycleScope.launch {
                        articleViewModel.searchArticle(s.toString()).collectLatest{ pagingData ->
                            adapter.submitData(pagingData)
                        }
                    }
                    true
                }else{
                    getArticles()
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

    private fun getArticles(){
        lifecycleScope.launch {
            articleViewModel.getArticles(sourceId).collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.btnClear.setBackgroundResource(R.drawable.baseline_search_24)
    }
}