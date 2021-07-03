package com.dinesh.Network.inspector.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.Network.R
import com.dinesh.Network.inspector.interfaces.NetworkInspectInterface
import com.dinesh.Network.inspector.adapters.NetworkInspectorAdapter
import com.dinesh.Network.inspector.model.NetworkInspect
import com.dinesh.Network.inspector.viewmodels.NIViewModel

class NetworkInspectActivity : AppCompatActivity(), NetworkInspectInterface {
    lateinit var recyclerView: RecyclerView
    lateinit var spinner: Spinner
    lateinit var viewModel: NIViewModel
    lateinit var adapter: NetworkInspectorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_tracker)
        viewModel = NIViewModel()
        spinner = findViewById(R.id.spinner)
        recyclerView = findViewById(R.id.network_inspect_list)
        adapter = NetworkInspectorAdapter(viewModel.getNetworkCallList(), this, this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        setSpinnerAdapater()
    }

    fun setSpinnerAdapater() {
        val filters = resources.getStringArray(R.array.NetworkFilter)
        spinner.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, filters)
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                val item = filters[position]
                var newDataSet = viewModel.getFilterResult(item)
                adapter.dataChange(newDataSet)

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    override fun selectedItem(item: NetworkInspect) {
        val intent = Intent(this, NetworkInspectorDetailActivity::class.java)
        intent.putExtra("NetworkInspect", item)
        startActivity(intent)
    }
}