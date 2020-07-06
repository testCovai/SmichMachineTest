package com.multicastdns.smitchmachinetest.view

import android.annotation.SuppressLint
import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.multicastdns.smitchmachinetest.R
import com.multicastdns.smitchmachinetest.databinding.ActivityMainBinding
import com.multicastdns.smitchmachinetest.listner.DNSDiscoveryListener
import com.multicastdns.smitchmachinetest.listner.DNSRegistrationListener
import com.multicastdns.smitchmachinetest.listner.DNSResolvedListener
import com.multicastdns.smitchmachinetest.model.ScanResult
import com.multicastdns.smitchmachinetest.utils.Constants
import com.multicastdns.smitchmachinetest.view.adapter.ScanResultAdapter
import com.multicastdns.smitchmachinetest.viewmodel.MainVM
import org.koin.android.ext.android.inject
import java.util.*

/**
 * Created by Nehru DN on 07/07/2020.
 */
class MainActivity : AppCompatActivity(), View.OnClickListener,
    DNSRegistrationListener.MyNewtorkRegistration,
    DNSDiscoveryListener.NetworkDiscovery, DNSResolvedListener.MyResolveListener {
    private var countDownTimer: CountDownTimer? = null
    private val mainViewModel: MainVM by inject()

    private var mNsdManager: NsdManager? = null
    private var isServiceRegistered = false
    private var isDiscoveryServiceRunning = false

    private var isPublishButtonClicked = false
    private var isScanButtonClicked = false

    private var scanResultAdapter: ScanResultAdapter? = null

    //    private var adapter: MyRecyclerViewAdapter? = null
    private var discoveryListener: DNSDiscoveryListener = DNSDiscoveryListener(this)
    private var registrationListener: DNSRegistrationListener = DNSRegistrationListener(this)
    private var serviceInfo: NsdServiceInfo? = null
    private lateinit var binding: ActivityMainBinding

    private val listOfDeviceConnected: MutableList<ScanResult> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        mNsdManager = getSystemService(Context.NSD_SERVICE) as NsdManager
        binding.btnPublish.setOnClickListener(this)
        binding.btnScan.setOnClickListener(this)

        scanResultAdapter = ScanResultAdapter(this@MainActivity)
//        adapter = MyRecyclerViewAdapter()
        binding.adapter = scanResultAdapter

        mainViewModel.scanResult.observe(this, Observer { setScanResultToAdapter(it) })
    }

    override fun onResume() {
        super.onResume()
        if (mNsdManager != null) {
            if (isPublishButtonClicked) {
                registerService(Constants.PORT)
            }
            if (isScanButtonClicked) {
                discoverService()
            }
        }
    }

    override fun onPause() {
        if (mNsdManager != null) {
            unRegisterService()
            stopDiscoverService()
        }
        if (countDownTimer != null) {
            stopTimer()
        }
        super.onPause()
    }

    /**
     * Handle click event here
     *
     * @param v View
     */
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnPublish -> {
                isPublishButtonClicked = true
                isScanButtonClicked = false
                registerService(Constants.PORT)
            }
            R.id.btnScan -> {
                isPublishButtonClicked = false
                isScanButtonClicked = true
                discoverService()
            }
        }
    }

    /**
     * Fetching services here
     */
    private fun discoverService() {
        if (!isDiscoveryServiceRunning) {
            isDiscoveryServiceRunning = true
            startTime()
            mNsdManager!!.discoverServices(
                Constants.SERVICE_TYPE,
                NsdManager.PROTOCOL_DNS_SD, discoveryListener
            )
        }
    }

    /**
     * Stop service here
     */
    private fun stopDiscoverService() {
        if (isDiscoveryServiceRunning) {
            isDiscoveryServiceRunning = false
            mNsdManager!!.stopServiceDiscovery(discoveryListener)
        }
    }

    /**
     * Register service here
     *
     * @param port
     */
    private fun registerService(port: Int) {
        serviceInfo = NsdServiceInfo()
        val tsLong = System.currentTimeMillis() / 1000
        serviceInfo!!.serviceName = Constants.SERVICE_NAME + tsLong.toString()
        serviceInfo!!.serviceType = Constants.SERVICE_TYPE
        serviceInfo!!.port = port
        if (!isServiceRegistered) {
            isServiceRegistered = true
            mNsdManager!!.registerService(
                serviceInfo, NsdManager.PROTOCOL_DNS_SD,
                registrationListener
            )
        }
    }

    /**
     * Unregister service here
     */
    private fun unRegisterService() {
        if (isServiceRegistered) {
            isServiceRegistered = false
            mNsdManager!!.unregisterService(registrationListener)
        }
    }

    override fun onDeviceRegistration(message: String?) {
        showMessage( message)
    }


    override fun onSeriveFound(serviceInfo: NsdServiceInfo?) {
        mNsdManager!!.resolveService(serviceInfo, DNSResolvedListener(this))
    }

    override fun discoveryStatus(message: String?) {
        showMessage( message)
    }

    override fun onDeviceFound(data: ScanResult?) {
        mainViewModel.scanResult.postValue(data)
    }

    private fun setScanResultToAdapter(data: ScanResult?) {
        listOfDeviceConnected.add(data!!)
        removeDuplicateDeviceItem(listOfDeviceConnected)
    }

    private fun startTime() {
        countDownTimer = object : CountDownTimer(5000, 1000) {
            // count down for 10seconds
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                binding.btnScan.text = "" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                stopDiscoverService()
                binding.btnScan.text = getString(R.string.btn_name_scan)
            }
        }.start()
    }

    private fun stopTimer() {
        binding.btnScan.text = getString(R.string.btn_name_scan)
        countDownTimer?.cancel()
    }

    private fun removeDuplicateDeviceItem(listOfDeviceConnected: MutableList<ScanResult>) {
        val treeList: TreeSet<ScanResult> =
            TreeSet<ScanResult>(Comparator { o1, o2 ->
                o1.ipAddress.toString().compareTo(o2.ipAddress.toString())
            })
        treeList.addAll(listOfDeviceConnected)
        this.listOfDeviceConnected.clear()
        this.listOfDeviceConnected.addAll(treeList)
        scanResultAdapter!!.updateAdapter(listOfDeviceConnected)
    }

    /**
     * Method to show the Toast message.
     *
     * @param message String which indicates the message to be displayed as Toast.
     */
    private fun showMessage(message: String?) {
        val toast = Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}