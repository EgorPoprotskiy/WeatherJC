package com.egorpoprotskiy.weatherjc

import android.app.Application

//11 Это главная точка входа в приложение, которая создается один раз и живет до тех пор, пока приложение не будет закрыто.

class WeatherApplication: Application() { //добавить запись в манифест.
    lateinit var appContainer: AppContainer
    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer()
    }
}