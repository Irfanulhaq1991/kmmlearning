package com.learning.dogify.android

import android.app.Application
import com.learning.dogify.di.initKoin

class DogifyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}