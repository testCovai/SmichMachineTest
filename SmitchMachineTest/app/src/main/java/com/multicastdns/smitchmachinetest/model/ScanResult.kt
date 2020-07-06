package com.multicastdns.smitchmachinetest.model

import java.net.InetAddress

/**
 * Created by Nehru DN on 07/07/2020.
 */
data class ScanResult(
    var serviceName: String,
    var serviceType: String,
    var portNo: Int,
    var ipAddress: InetAddress
)



