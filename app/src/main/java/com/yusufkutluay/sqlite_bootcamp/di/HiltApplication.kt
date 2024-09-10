package com.yusufkutluay.sqlite_bootcamp.di

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HiltApplication : Application(){
    // bunu manifest dosyasına bağlıyoruz
    // android:name=".di.HiltApplication" bu şekilde
}