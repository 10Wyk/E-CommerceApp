package com.wyk.e_commerceapp

import android.app.Application
import com.e_commerce.shared.DiHelper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ECommerceApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val koinApplication = startKoin {
            androidContext(this@ECommerceApplication)
        }

        DiHelper.init(koinApplication.koin)
    }
}