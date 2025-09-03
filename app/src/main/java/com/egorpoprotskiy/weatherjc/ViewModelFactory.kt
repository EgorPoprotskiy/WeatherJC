package com.egorpoprotskiy.weatherjc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.egorpoprotskiy.weatherjc.data.repository.WeatherRepository
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherViewModel

//9

// Эта фабрика создает экземпляр WeatherViewModel и передает ему репозиторий.
// Это и есть ручное внедрение зависимостей.
class ViewModelFactory(private val weatherRepository: WeatherRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        // Мы проверяем, что создается именно наш ViewModel.
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            // Возвращаем новый экземпляр ViewModel, передавая репозиторий.
            // @Suppress("UNCHECKED_CAST") — это нужно, чтобы убрать предупреждение
            // о небезопасном приведении типов.
            @Suppress("UNCHECKED_CASR")
            return WeatherViewModel(weatherRepository = weatherRepository) as T
        }
        // Если это не наш ViewModel, мы выбрасываем исключение.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}