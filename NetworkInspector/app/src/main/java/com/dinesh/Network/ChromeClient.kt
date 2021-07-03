package com.dinesh.Network

import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import com.dinesh.Network.inspector.interfaces.LogInterface

class ChromeClient(private val logInterface: LogInterface): WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress == 100) {
            logInterface.stopLoader()
        }
    }

    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        if(consoleMessage != null) {
            logInterface.webConsoleLog(consoleMessage)
        }
        return super.onConsoleMessage(consoleMessage)
    }

}