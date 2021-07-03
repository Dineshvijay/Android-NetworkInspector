package com.dinesh.Network.inspector.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebView
import android.widget.ProgressBar
import com.dinesh.Network.ChromeClient
import com.dinesh.Network.R
import com.dinesh.Network.WebAppClient
import com.dinesh.Network.inspector.interfaces.LogInterface
import com.dinesh.Network.inspector.interfaces.WebAppInterface
import com.dinesh.Network.inspector.model.NetworkInspect
import com.dinesh.Network.inspector.viewmodels.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), View.OnClickListener, LogInterface {
    lateinit var homeWebView: WebView
    lateinit var webAppInterface: WebAppInterface
    lateinit var menuFAB: FloatingActionButton
    lateinit var networkFAB: FloatingActionButton
    lateinit var logFAB: FloatingActionButton
    lateinit var viewModel: MainViewModel
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = MainViewModel()
        viewBinding()
        webAppInterface = WebAppInterface(this)
        configureWebView()
    }

    fun viewBinding(){
        progressBar = findViewById(R.id.progressBar)
        homeWebView = findViewById(R.id.homeWebview)
        menuFAB = findViewById(R.id.menuFAB)
        networkFAB = findViewById(R.id.networkFAB)
        logFAB = findViewById(R.id.logFAB)
        menuFAB.setOnClickListener(this)
        networkFAB.setOnClickListener(this)
        logFAB.setOnClickListener(this)
        networkFAB.visibility = View.GONE
        logFAB.visibility = View.GONE
    }


    fun configureWebView() {
        WebView.setWebContentsDebuggingEnabled(true);
        homeWebView.settings.domStorageEnabled = true
        homeWebView.settings.javaScriptEnabled = true
        homeWebView.addJavascriptInterface(webAppInterface, "Android")
        val logScript = assets.open("log_script.js").reader().readText()
        homeWebView.webViewClient = WebAppClient(webAppInterface, logScript)
        homeWebView.webChromeClient = ChromeClient(this)
        loadWebView()
    }

    fun loadWebView() {
        val url: String = "https://www.tutorialkart.com/kotlin-tutorial/"
        homeWebView.loadUrl(url)
    }

    override fun onClick(v: View?) {
        if(v == null) {
            return
        }
        when(v.id) {
            R.id.menuFAB -> showMenu()
            R.id.networkFAB -> showNetworkInspector()
            R.id.logFAB -> showLogInspector()
        }
    }

    fun showMenu() {
        if(networkFAB.visibility == View.GONE && logFAB.visibility == View.GONE) {
            networkFAB.visibility = View.VISIBLE
            logFAB.visibility = View.VISIBLE
        } else {
            networkFAB.visibility = View.GONE
            logFAB.visibility = View.GONE
        }
    }

    fun showNetworkInspector() {
        val intent = Intent(this, NetworkInspectActivity::class.java)
        startActivity(intent)
    }

    fun showLogInspector() {
        val intent = Intent(this, ConsoleLogActivity::class.java)
        startActivity(intent)
    }

    override fun webRequestLog(message: String) {
        viewModel.storeLogMessage(message)
    }

    override fun webConsoleLog(consoleMessage: ConsoleMessage) {
        viewModel.storeLogConsoleMessage(consoleMessage)
    }

    override fun stopLoader() {
        progressBar.visibility = View.GONE
    }
}