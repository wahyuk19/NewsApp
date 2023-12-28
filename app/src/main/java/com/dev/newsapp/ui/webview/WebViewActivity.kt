package com.dev.newsapp.ui.webview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.widget.Toast
import com.dev.newsapp.R
import com.dev.newsapp.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val webView = WebView(this)
        setContentView(webView)

        val url = intent.getStringExtra("url")

        if (url != null) {
            webView.loadUrl(url.toString())
        } else {
            webView.loadUrl("http://www.google.com")
            Toast.makeText(this@WebViewActivity, "unknown url", Toast.LENGTH_SHORT).show()
        }
    }
}