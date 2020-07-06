package com.multicastdns.smitchmachinetest.listner

import android.annotation.SuppressLint
import android.net.nsd.NsdManager.ResolveListener
import android.net.nsd.NsdServiceInfo
import com.multicastdns.smitchmachinetest.model.ScanResult
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Nehru DN on 07/07/2020.
 */
class DNSResolvedListener(private val myResolveListener: MyResolveListener) : ResolveListener {

    interface MyResolveListener {
        fun onDeviceFound(data: ScanResult?)
    }

    override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {}

    @SuppressLint("CheckResult")
    override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
        val data = ScanResult(
            serviceInfo.serviceName,
            serviceInfo.serviceType,
            serviceInfo.port,
            serviceInfo.host
        )
        val observable = Observable.just(data)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result -> myResolveListener.onDeviceFound(result) }
    }

}