package com.multicastdns.smitchmachinetest.di

import com.multicastdns.smitchmachinetest.viewmodel.MainVM
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module
private val mainVMModule = module {
    viewModel<MainVM>()
}
val activityModule = listOf(mainVMModule)