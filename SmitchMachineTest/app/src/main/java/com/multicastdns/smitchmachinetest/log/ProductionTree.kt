package com.multicastdns.smitchmachinetest.log

import android.util.Log
import com.multicastdns.smitchmachinetest.BuildConfig
import timber.log.Timber

class ProductionTree : Timber.Tree() {

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority == Log.ERROR && !BuildConfig.DEBUG) {
            // report error to crash report
            // e.g. Crashlytics.logException(t)
        }
    }

}