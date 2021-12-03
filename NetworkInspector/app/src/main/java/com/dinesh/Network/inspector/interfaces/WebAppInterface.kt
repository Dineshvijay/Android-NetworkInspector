package com.dinesh.Network.inspector.interfaces

import android.net.Uri
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import com.dinesh.Network.inspector.model.AppStore
import com.dinesh.Network.inspector.model.NetworkInspect
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

class WebAppInterface( private val logInterface: LogInterface) {
    private val requestHeaders: MutableMap<String, String> =  mutableMapOf()
    private var httpClient: OkHttpClient = OkHttpClient()

    fun addRequestHeaders(request: WebResourceRequest?) {
        if (request != null) {
            requestHeaders[request.url.toString()] = JSONObject(request.requestHeaders as Map<*, *>).toString()
            interceptWebRequest(request)
        }
    }

    @JavascriptInterface
    fun xhrLog(message: String) {
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
        GlobalScope.launch(IO) {
           httpCall(request.url.toString())
        }
    }

    private suspend fun httpCall(url: String) {
        coroutineScope {
            val networkCall = async {
                val connection = URL(url).openConnection() as HttpURLConnection
                try {
                    var data = ""
                    Log.i("🌐️", "requestMethod: ${connection.requestMethod}");
                    Log.i("🌐️️", "request Headers: ${connection.headerFields}")
                    Log.i("🌐", "contentType: ${connection.contentType}")
                    Log.i("🌐️", "responseCode: ${connection.responseCode}");
                    Log.i("🌐️", "responseMessage: ${connection.responseMessage}");
                    val lastPathSegment = Uri.parse(url).lastPathSegment
                    val paths = lastPathSegment?.split(".")
                    val name = paths?.first() ?: url
                    val status = connection.responseCode.toString() ?: ""
                    var reqHeaders = connection.headerFields.toString() ?: ""
                    var response = connection.responseMessage
                    var type = connection.contentType ?: "-"
                    type = type.split(";").first()
                    type = type.split("/").last()
                    var error = data
                    return@async NetworkInspect(name, url, status, reqHeaders, "" , response, type, error)

                } finally {
                    connection.disconnect()
                }
            }
            AppStore.networkCallList.add(networkCall.await())
        }
    }

    fun recordFailureCalls(request: WebResourceRequest?) {
        request?.let { webRequest ->
            var data = ""
            val lastPathSegment = webRequest.url.lastPathSegment
            val paths = lastPathSegment?.split(".")
            val name = paths?.first() ?: webRequest.url.toString()
            val status = "400"
            var reqHeaders = webRequest.requestHeaders.toString() ?: ""
            var response = ""
            var type = webRequest.requestHeaders["Accept"] ?: "-"
//            type = type.split(";").first()
//            type = type.split("/").last()
            var error = data
            val networkCall = NetworkInspect(name, webRequest.url.toString(), status, reqHeaders, "" , response, type, error)
            AppStore.networkCallList.add(networkCall)
        }
    }

    fun injectSCript(script: String){
        logInterface.injectScript("")
    }
}