package com.dinesh.Network.inspector.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import com.dinesh.Network.R
import com.dinesh.Network.inspector.model.NetworkInspect
import org.json.JSONObject

class NetworkInspectorDetailActivity : AppCompatActivity() {
    lateinit var callDetailTextView: TextView
    lateinit var spinner: Spinner
    lateinit var titleTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network_inspector_detail)
        titleTextView = findViewById(R.id.inspectorTitle)
        callDetailTextView = findViewById(R.id.call_detail)
        spinner = findViewById(R.id.spinner)
        callDetailTextView.movementMethod = ScrollingMovementMethod()
        setTitle()
        setSpinnerAdapater()
        parseResponse()
    }

    fun setTitle() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        titleTextView.text = networkIntercept.name
    }

    fun setSpinnerAdapater() {
        val filters = resources.getStringArray(R.array.ResponseFilter)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, filters)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                when(position) {
                    0 -> parseAll()
                    1 -> parseHeaders()
                    2 -> parseRequest()
                    3 -> parseResponse()
                    4 -> parseError()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    fun parseAll() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<b>URL:</b><p> ${networkIntercept.url}</p>\n" +
                "<b>Type:</b><p>${networkIntercept.type}</p>\n" +
                "<b>Status:</b> <p>${networkIntercept.status}</p>\n" +
                "<b>Request headers:</b><p>${networkIntercept.requestHeaders}</p>\n" +
                "<b>Method:</b><p>${networkIntercept.method}</p>\n" +
                "<b>Payload:</b><p>${networkIntercept.payload}</p>\n" +
                "<b>Response headers:</b><p>${networkIntercept.responseHeaders}</p>\n" +
                "<b>Response:</b><p>${networkIntercept.response}</p>\n" +
                "<b>Error:</b><p>${networkIntercept.error}</p>"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseHeaders() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<b>URL:</b><p> ${networkIntercept.url}</p>\n" +
                "<b>Type:</b><p>${networkIntercept.type}</p>\n" +
                "<b>Status:</b> <p>${networkIntercept.status}</p>\n" +
                "<b>Request headers:</b><p>${networkIntercept.requestHeaders}</p>\n" +
                "<b>Method:</b><p>${networkIntercept.method}</p>\n" +
                "<b>Payload:</b><p>${networkIntercept.payload}</p>\n" +
                "<b>Error:</b><p>${networkIntercept.error}</p>"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseRequest() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<b>Request:</b><p>${networkIntercept.requestHeaders}</p>\n"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseResponse() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<b>Response:</b><p>${networkIntercept.response}</p>\n"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseError() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<b>URL:</b><p> ${networkIntercept.url}</p>\n" +
                "<b>Type:</b><p>${networkIntercept.type}</p>\n" +
                "<b>Status:</b> <p>${networkIntercept.status}</p>\n" +
                "<b>Method:</b><p>${networkIntercept.method}</p>\n" +
                "<b>Body:</b><p>${networkIntercept.payload}</p>\n" +
                "<b>Error:</b><p>${networkIntercept.error}</p>"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }
}