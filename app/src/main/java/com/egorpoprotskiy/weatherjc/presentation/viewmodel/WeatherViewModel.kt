package com.egorpoprotskiy.weatherjc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.egorpoprotskiy.weatherjc.data.WeatherDto
import com.egorpoprotskiy.weatherjc.AppContainer
import com.egorpoprotskiy.weatherjc.data.ForecastDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

//9 - обработка текущего прогноза
//15 - добавляем обработку прогноза на 5 дней.

/**
 * Состояния UI для экрана погоды.
 * Этот sealed interface позволяет нам представить конечное число состояний.
 */

//Интерфейс для текущего прогноза погады
sealed interface WeatherState {
    object Loading: WeatherState
    data class Success(val weather: WeatherDto) : WeatherState
    data class Error(val message: String) : WeatherState
}
//Нитерфейс для 5-дневного прогноза
sealed interface ForecastState {
    object Loading: ForecastState
    data class Success(val forecast: ForecastDto) : ForecastState
    data class Error(val message: String) : ForecastState
}
open class WeatherViewModel(
//    private val weatherRepository: WeatherRepository
    private val appContainer: AppContainer
): ViewModel() {
    private val weatherRepository = appContainer.weatherRepository
    private val _weatherState = MutableStateFlow<WeatherState>(WeatherState.Loading)
    val weatherState: StateFlow<WeatherState> = _weatherState

    private val _forecastState = MutableStateFlow<ForecastState>(ForecastState.Loading)
    val forecastState: StateFlow<ForecastState> = _forecastState
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
            _weatherState.value = WeatherState.Loading
            _forecastState.value = ForecastState.Loading
            try {
                // 1. Попытка выполнить сетевой запрос
                // Вызываем репозиторий для получения данных.
                val weatherData = weatherRepository.getWeather(
                    q = city
                )
                val forecastData = weatherRepository.getWeatherForecast(
                    city = city
                )
                // 2. Если запрос успешен, устанавливаем состояние успеха
                _weatherState.value = WeatherState.Success(weatherData)
                _forecastState.value = ForecastState.Success(forecastData)
            } catch (e: IOException) {
                // 3. Обработка ошибки сети (нет интернета, таймаут).
                _weatherState.value = WeatherState.Error("Ошибка сети. Проверьте подключение к интернету")
                _forecastState.value = ForecastState.Error("Ошибка сети. Проверьте подключение к интернету")
            } catch (e: HttpException) {
                // 4. Обработка ошибки сервера (например, неверный ключ или город).
                _weatherState.value = WeatherState.Error("Ошибка HTTP. Код: ${e.code()}. Сообщение: ${e.message()}")
                _forecastState.value = ForecastState.Error("Ошибка HTTP. Код: ${e.code()}. Сообщение: ${e.message()}")
            } catch (e: Exception) {
                // 5. Обработка других, неожиданных ошибок.
                _weatherState.value = WeatherState.Error(e.message ?: "Произошла неизвестная ошибка")
                _forecastState.value = ForecastState.Error(e.message ?: "Произошла неизвестная ошибка")
            }
        }
    }
}