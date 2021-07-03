package com.dinesh.Network.inspector.model

import java.io.Serializable

data class NetworkInspect(
    val name: String,
    val url: String,
    val status: String,
    val requestHeaders: String,
    val responseHeaders: String,
    val response: String,
    val type: String,
    val error: String): Serializable
