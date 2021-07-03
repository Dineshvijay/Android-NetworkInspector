package com.dinesh.Network.inspector.interfaces

import android.content.Context
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import com.dinesh.Network.inspector.model.NetworkInspect
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class WebAppInterface( private val logInterface: LogInterface) {
    private val requestHeaders: MutableMap<String, String> =  mutableMapOf()
    private var httpClient: OkHttpClient = OkHttpClient()

    fun addRequestHeaders(request: WebResourceRequest) {
        requestHeaders[request.url.toString()] = JSONObject(request.requestHeaders as Map<*, *>).toString()
        interceptWebRequest(request)
    }

    @JavascriptInterface
    fun logAjaxCall(message: String) {
        val jsonObject = JSONObject(message)
        val url = jsonObject["url"].toString()
        val request = requestHeaders[url].toString()
        jsonObject.put("requestHeaders", request)
        logInterface.webRequestLog(jsonObject.toString())
        requestHeaders.remove(url)
        Log.i("🔔", jsonObject["type"].toString());
        Log.i("🤖", jsonObject.toString());
    }

    private fun interceptWebRequest(request: WebResourceRequest) {
        GlobalScope.launch {
            val headers = request.requestHeaders
            makeHttpCall(request.url.toString(), headers)
        }
    }

    private fun makeHttpCall(url: String, httpHeaders: Map<String, String>) {
        val headers = Headers.of(httpHeaders)
        val request = Request.Builder()
            .method("GET", null)
            .headers(headers)
            .url(url)
            .build()
        httpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val payload = JSONObject()
                payload.put("requestHeaders", request.headers())
                payload.put("responseHeaders", "-")
                payload.put("url", request.url().toString())
                payload.put("response", "-")
                payload.put("status", "-")
                payload.put("type", httpHeaders["Accept"].toString())
                payload.put("error", e.message.toString())
                logInterface.webRequestLog(payload.toString())
                Log.i("🚨", payload.toString())
            }
            override fun onResponse(call: Call, response: Response) {
                val payload = JSONObject()
                payload.put("requestHeaders", request.headers())
                payload.put("responseHeaders", response.headers())
                payload.put("url", request.url().toString())
                //payload.put("response", response.body().string())
                payload.put("response", "-")
                payload.put("status", response.code().toString())
                payload.put("type", httpHeaders["Accept"].toString())
                payload.put("error", "-")
                logInterface.webRequestLog(payload.toString())
                Log.i("🟢", payload.toString())
            }
        })
    }
}