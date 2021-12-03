package com.dinesh.Network

import android.graphics.Bitmap
import android.net.http.SslError
import android.os.Message
import android.util.Log
import android.webkit.*
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

    override fun onLoadResource(view: WebView?, url: String?) {
        super.onLoadResource(view, url)
    }

    override fun onReceivedError(
        view: WebView?,
        request: WebResourceRequest?,
        error: WebResourceError?
    ) {
        super.onReceivedError(view, request, error)
        Log.i("💔", request?.url.toString())
        webAppInterface.recordFailureCalls(request)
    }

    override fun onReceivedHttpError(
        view: WebView?,
        request: WebResourceRequest?,
        errorResponse: WebResourceResponse?
    ) {
        super.onReceivedHttpError(view, request, errorResponse)
        Log.i("💔", request?.url.toString())
        webAppInterface.recordFailureCalls(request)
    }

    override fun onPageCommitVisible(view: WebView?, url: String?) {
        super.onPageCommitVisible(view, url)

    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        webAppInterface.injectSCript("")
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return true
    }

}