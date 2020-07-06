package com.multicastdns.smitchmachinetest.listner

import android.net.nsd.NsdManager.DiscoveryListener
import android.net.nsd.NsdServiceInfo

/**
 * Created by Nehru DN on 07/07/2020.
 */
class DNSDiscoveryListener(discovery: NetworkDiscovery?) : DiscoveryListener {
    private var networkDiscovery: NetworkDiscovery? = null

    interface NetworkDiscovery {
        fun onSeriveFound(serviceInfo: NsdServiceInfo?)
        fun discoveryStatus(message: String?)
    }

    override fun onStartDiscoveryFailed(
        serviceType: String,
        errorCode: Int
    ) {
        networkDiscovery!!.discoveryStatus("Start fetching data failed")
    }

    override fun onStopDiscoveryFailed(
        serviceType: String,
        errorCode: Int
    ) {
        networkDiscovery!!.discoveryStatus("Stop fetching data failed")
    }

    override fun onDiscoveryStarted(serviceType: String) {
        networkDiscovery!!.discoveryStatus("Fetching data...")
    }

    override fun onDiscoveryStopped(serviceType: String) {
        networkDiscovery!!.discoveryStatus("Fetching data stop")
    }

    override fun onServiceFound(serviceInfo: NsdServiceInfo) {
        networkDiscovery!!.onSeriveFound(serviceInfo)
    }

    override fun onServiceLost(serviceInfo: NsdServiceInfo) {
        networkDiscovery!!.discoveryStatus("Service lost")
    }

    init {
        this.networkDiscovery = discovery
    }
}