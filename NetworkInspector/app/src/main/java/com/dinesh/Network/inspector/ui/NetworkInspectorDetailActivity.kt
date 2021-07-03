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
        val htmlText = "<h2>URL:</h2><p> ${networkIntercept.url}</p>\n" +
                "<h2>Type: ${networkIntercept.type}</h2>\n" +
                "<h2>Status: ${networkIntercept.status}</h2>\n" +
                "<h2>Request headers:</h2><p>${networkIntercept.requestHeaders}</p>\n" +
                "<h2>Response headers:</h2><p>${networkIntercept.responseHeaders}</p>\n" +
                "<h2>Response:</h2><p>${networkIntercept.response}</p>\n" +
                "<h2>Error:</h2><p>${networkIntercept.error}</p>"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseHeaders() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<h2>URL:</h2><p> ${networkIntercept.url}</p>\n" +
                "<h2>Type: ${networkIntercept.type}</h2>\n" +
                "<h2>Status: ${networkIntercept.status}</h2>\n" +
                "<h2>Request headers:</h2><p>${networkIntercept.requestHeaders}</p>\n" +
                "<h2>Error:</h2><p>${networkIntercept.error}</p>"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseRequest() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<h2>Request:</h2><p>${networkIntercept.requestHeaders}</p>\n"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseResponse() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<h2>Response:</h2><p>${networkIntercept.response}</p>\n"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }

    fun parseError() {
        val networkIntercept = intent.getSerializableExtra("NetworkInspect") as NetworkInspect
        val htmlText = "<h2>URL:</h2><p> ${networkIntercept.url}</p>\n" +
                "<h2>Type: ${networkIntercept.type}</h2>\n" +
                "<h2>Status: ${networkIntercept.status}</h2>\n" +
                "<h2>Error:</h2><p>${networkIntercept.error}</p>"
        callDetailTextView.text = Html.fromHtml(htmlText, 0)
    }
}