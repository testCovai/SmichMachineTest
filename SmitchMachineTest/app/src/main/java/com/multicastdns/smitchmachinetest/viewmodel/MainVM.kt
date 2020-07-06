package com.multicastdns.smitchmachinetest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.multicastdns.smitchmachinetest.model.ScanResult

/**
 * Created by Nehru DN on 07/07/2020.
 */
class MainVM : ViewModel() {
    val scanResult = MutableLiveData<ScanResult>()
}