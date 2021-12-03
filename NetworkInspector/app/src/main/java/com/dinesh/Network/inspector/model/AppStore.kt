package com.dinesh.Network.inspector.model

import android.webkit.ConsoleMessage

object AppStore {
    var networkCallList: MutableList<NetworkInspect> = java.util.Collections.synchronizedList(mutableListOf<NetworkInspect>())
    var consoleLogMessages: String = ""
}