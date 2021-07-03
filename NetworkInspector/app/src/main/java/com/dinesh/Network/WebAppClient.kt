package com.dinesh.Network

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.dinesh.Network.inspector.interfaces.WebAppInterface
import org.jsoup.Jsoup
import java.io.InputStream
import java.lang.Exception

class WebAppClient(private val webAppInterface: WebAppInterface, private val logScript: String): WebViewClient() {

    private fun webResponse(request: WebResourceRequest): WebResourceResponse? {
        val content = request.requestHeaders["Accept"].toString()
        if (content.contains("text/html")) {
            return try {
                val doc = Jsoup.connect(request.url.toString()).get()
                val script = doc.createElement("script")
                script.append(logScript)
                doc.head().appendChild(script)
                val inputStream: InputStream = doc.toString().byteInputStream()
                Log.i("🟢", "Injected script")
                WebResourceResponse("text/html", "UTF8", inputStream)
            } catch (e: Exception) {
                Log.i("🔴", "Injected script")
                null
            }
        }
        return null
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }

    override fun shouldInterceptRequest(
        view: WebView,
        request: WebResourceRequest
    ): WebResourceResponse? {
         webAppInterface.addRequestHeaders(request)
         return webResponse(request)
    }
}