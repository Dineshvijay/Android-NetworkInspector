package com.dinesh.Network.inspector.viewmodels

import android.net.Uri
import android.webkit.ConsoleMessage
import androidx.lifecycle.ViewModel
import com.dinesh.Network.inspector.model.AppStore
import com.dinesh.Network.inspector.model.NetworkInspect
import org.json.JSONObject

class MainViewModel: ViewModel() {

    fun storeLogMessage(message: String) {
        val jsonObject = JSONObject(message)
        val urlString = jsonObject["url"].toString()
        val requestHeaders = jsonObject["requestHeaders"].toString()
        val responseHeaders = jsonObject["responseHeaders"].toString()
        val response = jsonObject["response"].toString()
        val status = jsonObject["status"].toString()
        val error = jsonObject["error"].toString()
        val type = jsonObject["type"].toString()
        val lastPathSegment = Uri.parse(urlString).lastPathSegment
        val paths = lastPathSegment?.split(".")
        val name = paths?.first() ?: urlString
        var callType = paths?.last() ?: type
        if(type.lowercase() == "xhr") {
            callType = type
        }
        val networkInspect = NetworkInspect(name, urlString, status, requestHeaders, responseHeaders , response, callType, error)
        AppStore.networkCallList.add(networkInspect)
    }

    fun storeLogConsoleMessage(consoleMessage: ConsoleMessage) {
        var messageType: String = consoleMessage.messageLevel().toString()
        var message: String = consoleMessage.message()
        var newMessage = "<h2>${messageType}</h2>\n" + "<p>${message}</p>\n"
        var oldMessage = AppStore.consoleLogMessages
        AppStore.consoleLogMessages = oldMessage + newMessage
    }
}