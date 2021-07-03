package com.dinesh.Network.inspector.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import com.dinesh.Network.R
import com.dinesh.Network.inspector.model.NetworkInspect
import com.dinesh.Network.inspector.viewmodels.ConsoleViewModel

class ConsoleLogActivity : AppCompatActivity() {
    lateinit var consoleMessage: TextView
    lateinit var spinner: Spinner
    lateinit var titleTextView: TextView
    lateinit var viewModel: ConsoleViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_console_log)
        viewModel = ConsoleViewModel()
        titleTextView = findViewById(R.id.inspectorTitle)
        consoleMessage = findViewById(R.id.consoleMessage)
        spinner = findViewById(R.id.spinner)
        consoleMessage.movementMethod = ScrollingMovementMethod()
        setTitle()
        setSpinnerAdapater()
        showLogMessage()
    }

    fun setTitle() {
        titleTextView.text = "Console Log"
    }

    fun setSpinnerAdapater() {
        val filters = resources.getStringArray(R.array.LogFilter)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, filters)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    fun showLogMessage() {
        val htmlText = viewModel.getConsoleLogMessage()
        Log.i("📣", htmlText)
        consoleMessage.text = Html.fromHtml(htmlText, Html.FROM_HTML_MODE_LEGACY)
    }
}