package com.dinesh.Network.inspector.model

import android.webkit.ConsoleMessage

object AppStore {
    var networkCallList: MutableList<NetworkInspect> = mutableListOf()
    var consoleLogMessages: String = ""
}