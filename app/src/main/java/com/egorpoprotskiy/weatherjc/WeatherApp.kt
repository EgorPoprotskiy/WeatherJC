package com.egorpoprotskiy.weatherjc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherViewModel
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.egorpoprotskiy.weatherjc.ui.screens.MainScreen

//1
@Composable
fun WeatherApp() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        // Получаем контекст приложения, чтобы иметь доступ к нашему AppContainer.
        val application = LocalContext.current.applicationContext as WeatherApplication
        // Используем нашу фабрику для создания ViewModel.
        val weatherViewModel: WeatherViewModel = viewModel(
            factory = ViewModelFactory(application.appContainer)
        )
        // Передаем созданный ViewModel в MainScreen.
        MainScreen(
            viewModel = weatherViewModel
        )
    }
}