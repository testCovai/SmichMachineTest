package com.multicastdns.smitchmachinetest.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.multicastdns.smitchmachinetest.R
import com.multicastdns.smitchmachinetest.model.ScanResult
import java.util.*

/**
 * Created by Nehru DN on 07/07/2020.
 */
class ScanResultAdapter(private val mContext: Context) :
    RecyclerView.Adapter<ScanResultAdapter.ViewHolder>() {
    private val scanResults: MutableList<ScanResult> = ArrayList()

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        i: Int
    ): ViewHolder {
        val view = LayoutInflater.from(mContext)
            .inflate(R.layout.adapter_scan_result_item, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        i: Int
    ) {
        val data = scanResults[i]
        viewHolder.tvServiceName.text = "Service Name  : " + data.serviceName
        viewHolder.tvServiceType.text = "Service Type  : " + data.serviceType
        viewHolder.tvIPPort.text = "Port  : " + data.portNo
        viewHolder.tvIPAddress.text = "IP Address  : ${data.ipAddress.hostAddress}"
    }

    override fun getItemCount(): Int {
        return scanResults.size
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var tvServiceName: AppCompatTextView = itemView.findViewById(R.id.tvServiceName)
        var tvServiceType: AppCompatTextView = itemView.findViewById(R.id.tvServiceType)
        var tvIPAddress: AppCompatTextView = itemView.findViewById(R.id.tvIPAddress)
        var tvIPPort: AppCompatTextView = itemView.findViewById(R.id.tvIPPort)
    }

    fun updateAdapter(scanResults: MutableList<ScanResult>) {
        this.scanResults.clear()
        this.scanResults.addAll(scanResults)
        notifyDataSetChanged()
    }
}