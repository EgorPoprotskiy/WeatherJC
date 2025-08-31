package com.egorpoprotskiy.weatherjc.presentation.viewmodel

import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

//9

//@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
): ViewModel() {

    suspend fun getWeather() {

    }
}