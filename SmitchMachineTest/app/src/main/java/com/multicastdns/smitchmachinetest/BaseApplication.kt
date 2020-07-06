package com.multicastdns.smitchmachinetest

import android.app.Application
import org.koin.android.ext.android.get
import org.koin.android.ext.android.startKoin
import timber.log.Timber
import com.multicastdns.smitchmachinetest.di.activityModule
import com.multicastdns.smitchmachinetest.di.appModule


/**
 * Created by Nehru DN on 07/07/2020.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // get all modules
        val moduleList = appModule + activityModule
        // set the module list
        startKoin(this, moduleList)

        // get log tree implementation
        val tree: Timber.Tree = get()
        Timber.plant(tree)
    }
}