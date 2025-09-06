package com.egorpoprotskiy.weatherjc

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherViewModel
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherState

//1
@Composable
fun WeatherApp(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel
) {
    // Собираем состояние из ViewModel. Compose будет перерисовывать UI при каждом изменении.
    val weatherState by viewModel.weatherState.collectAsState()

    // Используем `when` для отображения разных UI в зависимости от состояния.
    when (weatherState) {
        is WeatherState.Loading -> {
            // Показываем индикатор загрузки
            LoadingScreen(modifier = modifier)
        }
        is WeatherState.Success -> {
            // Показываем данные о погоде
            WeatherInfoScreen(
                modifier = modifier,
                weather = (weatherState as WeatherState.Success).weather
            )
        }
        is WeatherState.Error -> {
            // Показываем сообщение об ошибке
            ErrorScreen(
                modifier = modifier,
                message = (weatherState as WeatherState.Error).message
            )
        }
    }
}

// Компонент для отображения экрана загрузки
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
        Text(text = "Загрузка...", modifier = Modifier.padding(top = 16.dp))
    }
}

// Компонент для отображения информации о погоде
@Composable
fun WeatherInfoScreen(
    modifier: Modifier = Modifier,
    weather: com.egorpoprotskiy.weatherjc.data.WeatherDto
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = weather.name, fontSize = 32.sp)
        Text(text = "${weather.main.temp.toInt()}°C", fontSize = 64.sp, modifier = Modifier.padding(top = 8.dp))
        Text(text = weather.weather.firstOrNull()?.description ?: "Нет данных", fontSize = 18.sp)
    }
}

// Компонент для отображения экрана ошибки
@Composable
fun ErrorScreen(modifier: Modifier = Modifier, message: String) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Ошибка:", fontSize = 24.sp, color = androidx.compose.ui.graphics.Color.Red)
        Text(text = message, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview
@Composable
fun WeatherAppPreview() {
    MaterialTheme {
        Text(text = "Здесь будет отображаться погода!")
    }
}