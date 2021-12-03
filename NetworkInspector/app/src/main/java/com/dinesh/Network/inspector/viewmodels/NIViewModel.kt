package com.dinesh.Network.inspector.viewmodels

import androidx.lifecycle.ViewModel
import com.dinesh.Network.inspector.model.AppStore
import com.dinesh.Network.inspector.model.NetworkInspect

class NIViewModel: ViewModel() {

    fun getNetworkCallList(): List<NetworkInspect> {
        return AppStore.networkCallList
    }

    fun getFilterResult(name: String): List<NetworkInspect> {
        var filter = name
        if (name.lowercase() == "all") {
            return AppStore.networkCallList
        } else if(name.lowercase() == "js") {
            filter = "javascript"
        }
        return AppStore.networkCallList.filter { it.type?.lowercase() == filter.lowercase() }
    }
}