package com.multicastdns.smitchmachinetest.listner

import android.net.nsd.NsdManager.RegistrationListener
import android.net.nsd.NsdServiceInfo

/**
 * Created by Nehru DN on 07/07/2020.
 */
class DNSRegistrationListener(registration: MyNewtorkRegistration?) :
    RegistrationListener {
    private var registration: MyNewtorkRegistration? = null

    interface MyNewtorkRegistration {
        fun onDeviceRegistration(message: String?)
    }

    override fun onRegistrationFailed(
        serviceInfo: NsdServiceInfo,
        errorCode: Int
    ) {
        registration!!.onDeviceRegistration("Registration failed")
    }

    override fun onUnregistrationFailed(
        serviceInfo: NsdServiceInfo,
        errorCode: Int
    ) {
        registration!!.onDeviceRegistration("Unregistration failed")
    }

    override fun onServiceRegistered(serviceInfo: NsdServiceInfo) {
        registration!!.onDeviceRegistration("Service registered")
    }

    override fun onServiceUnregistered(serviceInfo: NsdServiceInfo) {
        registration!!.onDeviceRegistration("Service unregistered")
    }

    init {
        this.registration = registration
    }
}