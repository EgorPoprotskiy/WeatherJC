package com.egorpoprotskiy.weatherjc

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//8 + добавить "android:name=".WeatherApplication" в манифест

@HiltAndroidApp
class WeatherApplication: Application() {

}