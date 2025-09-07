package com.egorpoprotskiy.weatherjc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.weatherjc.BuildConfig
import com.egorpoprotskiy.weatherjc.data.WeatherDto
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

//9

/**
 * Состояния UI для экрана погоды.
 * Этот sealed interface позволяет нам представить конечное число состояний.
 */
sealed interface WeatherState {
    object Loading: WeatherState
    data class Success(val weather: WeatherDto) : WeatherState
    data class Error(val message: String) : WeatherState
}
class WeatherViewModel(
    private val weatherRepository: WeatherRepository
): ViewModel() {
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState

    init {
        // Загрузка данных при инициализации ViewModel.
        getWeather(city = "Moscow")
    }
    /**
     * Получает данные о погоде для указанного города.
     * @param city Название города.
     */
    fun getWeather(city: String) {
        viewModelScope.launch {
            try {
                _weatherState.value = WeatherState.Loading
                // 1. Попытка выполнить сетевой запрос
                // Вызываем репозиторий для получения данных.
                val weatherData = weatherRepository.getWeather(
                    q = city,
                    appid = BuildConfig.OPEN_WEATHER_MAP_API_KEY,
                    units = "metric"
                )
                // 2. Если запрос успешен, устанавливаем состояние успеха
                _weatherState.value = WeatherState.Success(weatherData)
            } catch (e: IOException) {
                // 3. Обработка ошибки сети (нет интернета, таймаут).
                _weatherState.value = WeatherState.Error("Ошибка сети. Проверьте подключение к интернету")
            } catch (e: HttpException) {
                // 4. Обработка ошибки сервера (например, неверный ключ или город).
                _weatherState.value = WeatherState.Error("Ошибка HTTP. Код: ${e.code()}. Сообщение: ${e.message()}")
            } catch (e: Exception) {
                // 5. Обработка других, неожиданных ошибок.
                _weatherState.value = WeatherState.Error(e.message ?: "Произошла неизвестная ошибка")
            }
        }
    }
}