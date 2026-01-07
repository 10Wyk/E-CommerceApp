package com.wyk.e_commerceapp

import android.app.Application
import com.e_commerce.shared.di.DiHelper
import com.google.firebase.Firebase
import com.google.firebase.initialize
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ECommerceApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val koinApplication = startKoin {
            androidContext(this@ECommerceApplication)
        }
        DiHelper.init(koinApplication.koin)

        Firebase.initialize(this)
        GoogleAuthProvider.create(credentials = GoogleAuthCredentials(serverId = BuildConfig.WEB_CLIENT_ID))
    }
}