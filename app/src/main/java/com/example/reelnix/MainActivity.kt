package com.example.reelnix

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    private lateinit var myWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            AndroidView(
                factory = { context ->
                    WebView(context).apply {
                        myWebView = this // Assign the WebView instance
                        settings.javaScriptEnabled = true

                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                                val uri = Uri.parse(url)
                                val host = uri.host

                                // Check if the URL's domain is in our list of "external browser" domains
                                if (host in listOf("downloadwella.com", "kimoitv.com", "meetdownload.com")) {
                                    // Open the URL in an external browser
                                    Intent(Intent.ACTION_VIEW, uri).apply {
                                        startActivity(this)
                                    }
                                    return true // Indicate that we've handled the URL
                                } else {
                                    // Otherwise, let the WebView handle the URL internally
                                    return false
                                }
                            }
                        }

                        loadUrl("https://reelnix.vercel.app")
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    // Handle back button presses to navigate within WebView
    override fun onBackPressed() {
        if (myWebView.canGoBack()) {
            myWebView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}