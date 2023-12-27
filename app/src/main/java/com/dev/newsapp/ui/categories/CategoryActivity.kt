package com.dev.newsapp.ui.categories

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dev.newsapp.R
import com.dev.newsapp.data.model.Categories
import com.dev.newsapp.databinding.ActivityCategoryBinding
import com.dev.newsapp.ui.source.SourceActivity
import com.dev.newsapp.utils.inputStreamToString
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var adapter: CategoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categories = createCategoriesFromRawAsset()
        adapter = CategoryAdapter {category ->
            doClick(category)
        }

        with(binding){
            rvCat.layoutManager = LinearLayoutManager(this@CategoryActivity)
            rvCat.adapter = adapter
            adapter.setListCategories(categories)
        }

    }

    private fun doClick(category: String) {
        val intent = Intent(this,SourceActivity::class.java)
        intent.putExtra("category",category)
        startActivity(intent)
    }

    private fun createCategoriesFromRawAsset(): List<Categories> {
        val menuJson: JsonElement =
            JsonParser.parseString(inputStreamToString(resources.openRawResource(R.raw.categories)))
        val listType = object : TypeToken<List<Categories?>?>() {}.type
        return Gson().fromJson(menuJson.asJsonObject["categories"].asJsonArray, listType)
    }
}