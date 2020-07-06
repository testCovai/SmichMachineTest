package com.multicastdns.smitchmachinetest.di

import com.multicastdns.smitchmachinetest.BuildConfig
import com.multicastdns.smitchmachinetest.log.ProductionTree
import org.koin.dsl.module.module
import timber.log.Timber


private val appModules = module {

    single {
        if (BuildConfig.DEBUG) Timber.DebugTree()
        else ProductionTree()
    }

}

val appModule = listOf(appModules)
