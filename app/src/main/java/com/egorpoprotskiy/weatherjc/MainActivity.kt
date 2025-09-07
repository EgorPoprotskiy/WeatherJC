package com.egorpoprotskiy.weatherjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherViewModel
import com.egorpoprotskiy.weatherjc.ui.theme.WeatherJCTheme
//12
class MainActivity : ComponentActivity() {

    // Ленивая инициализация ViewModel
    // Мы используем viewModels и фабрику, чтобы получить наш ViewModel.
    private val viewModel: WeatherViewModel by viewModels {
        // Получаем экземпляр нашего приложения.
        val application = application as WeatherApplication
        // Возвращаем фабрику, передав ей репозиторий из нашего контейнера.
        ViewModelFactory(application.appContainer.weatherRepository)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherJCTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Передаем наш ViewModel в главный экран.
                    WeatherApp(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}
