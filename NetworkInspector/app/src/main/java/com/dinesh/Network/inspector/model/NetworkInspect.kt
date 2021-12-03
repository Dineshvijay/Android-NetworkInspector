package com.dinesh.Network.inspector.model

import java.io.Serializable

data class NetworkInspect(
    val name: String? = null,
    val url: String? = null,
    val status: String? = null,
    val requestHeaders: String? = null,
    val responseHeaders: String? = null,
    val response: String? = null,
    val type: String? = null,
    val payload: String? = null,
    val method: String? = null,
    val error: String? = null): Serializable
