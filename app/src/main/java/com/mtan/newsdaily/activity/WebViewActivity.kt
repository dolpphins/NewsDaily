package com.mtan.newsdaily.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.mtan.newsdaily.R

class WebViewActivity: AppCompatActivity() {

    private lateinit var mWebView: WebView

    private lateinit var mUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        mUrl = intent.getStringExtra("url")

        mWebView = findViewById(R.id.web_view)
        mWebView.loadUrl(mUrl)
    }

}