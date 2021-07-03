package com.dinesh.Network.inspector.viewmodels

import androidx.lifecycle.ViewModel
import com.dinesh.Network.inspector.model.AppStore

class ConsoleViewModel: ViewModel() {
    fun getConsoleLogMessage(): String {
        return  AppStore.consoleLogMessages
    }
}