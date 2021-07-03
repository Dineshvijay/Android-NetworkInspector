package com.dinesh.Network.inspector.interfaces

import android.webkit.ConsoleMessage
import com.dinesh.Network.inspector.model.NetworkInspect

interface NetworkInspectInterface {
    fun selectedItem(item: NetworkInspect)
}

interface LogInterface {
    fun webRequestLog(message: String)
    fun webConsoleLog(consoleMessage : ConsoleMessage)
    fun stopLoader()
}