package com.dinesh.Network.inspector.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.Network.R
import com.dinesh.Network.inspector.interfaces.NetworkInspectInterface
import com.dinesh.Network.inspector.model.NetworkInspect

class NetworkInspectorAdapter(private var dataStore: List<NetworkInspect>,
                              private val networkInterface: NetworkInspectInterface,
                              private val mContext: Context): RecyclerView.Adapter<NetworkInspectorAdapter.NetworkInspectorViewHolder>() {

    class NetworkInspectorViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val statusIcon: ImageView = itemView.findViewById(R.id.statusIcon)
        val callName: TextView = itemView.findViewById(R.id.callName)
        val url: TextView = itemView.findViewById(R.id.url)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetworkInspectorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.network_call_item, parent, false)
        return NetworkInspectorViewHolder(view)
    }

    override fun onBindViewHolder(holder: NetworkInspectorViewHolder, position: Int) {
        val networkInspect = dataStore[position]
        holder.callName.text = networkInspect.name
        holder.url.text = networkInspect.url
        holder.statusIcon.setImageDrawable(getStatusIcon(networkInspect.status))
        holder.itemView.setOnClickListener {
            networkInterface.selectedItem(networkInspect)
        }
    }

    private fun getStatusIcon(status: String): Drawable {
        val statusCode = status.toInt()
        if (statusCode >= 400) {
           return mContext.resources.getDrawable(R.drawable.ic_red_status_24)
        }
        return mContext.resources.getDrawable(R.drawable.ic_green_status_24)

    }

    override fun getItemCount(): Int {
        return dataStore.size
    }

    fun dataChange(newDataStore: List<NetworkInspect>) {
        this.dataStore = newDataStore
        notifyDataSetChanged()
    }
}