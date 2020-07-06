package com.multicastdns.smitchmachinetest.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.multicastdns.smitchmachinetest.R
//import com.multicastdns.smitchmachinetest.BR
import com.multicastdns.smitchmachinetest.databinding.AdapterScanResultItemBinding
import com.multicastdns.smitchmachinetest.model.ScanResult
import java.util.ArrayList

class MyRecyclerViewAdapter : RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val scanResults: MutableList<ScanResult> = ArrayList()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding: AdapterScanResultItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.adapter_scan_result_item, parent, false
            )
        return ViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val dataModel =
            scanResults[position]
        holder.bind(dataModel)
    }

    override fun getItemCount(): Int {
        return scanResults.size
    }

    inner class ViewHolder(var itemRowBinding: AdapterScanResultItemBinding) :
        RecyclerView.ViewHolder(itemRowBinding.root) {
        fun bind(obj: ScanResult?) {
//            itemRowBinding.setVariable(BR.scanResult, obj)
            itemRowBinding.executePendingBindings()
        }
    }
    fun updateAdapter(scanResults: MutableList<ScanResult>) {
        this.scanResults.clear()
        this.scanResults.addAll(scanResults)
        notifyDataSetChanged()
    }

}