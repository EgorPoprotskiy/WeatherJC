package com.egorpoprotskiy.weatherjc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.egorpoprotskiy.weatherjc.data.Clouds
import com.egorpoprotskiy.weatherjc.data.Coord
import com.egorpoprotskiy.weatherjc.data.Main
import com.egorpoprotskiy.weatherjc.data.Sys
import com.egorpoprotskiy.weatherjc.data.Weather
import com.egorpoprotskiy.weatherjc.data.WeatherDto
import com.egorpoprotskiy.weatherjc.data.Wind
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherState
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel
) {
    // Состояние для хранения текста в поле поиска
    var city by remember { mutableStateOf("") }
    // Собираем состояние из ViewModel. Compose будет перерисовывать UI при каждом изменении.
    val weatherState by viewModel.weatherState.collectAsState()
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            //Реализация поиска города
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Название города") },
                    modifier = Modifier.weight(1f),
                    colors = OutlinedTextFieldDefaults.colors(
                        // Цвет текста будет меняться в зависимости от темы.
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        // Цвет метки (label) тоже будет меняться.
                        focusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Button(
                    onClick = {
                        if (city.isNotBlank()) {
                            viewModel.getWeather(city)
                        }
                    },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Поиск"
                    )
                }
            }
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
    weather: WeatherDto
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = weather.name, fontSize = 32.sp)
        Text(
            text = "${weather.main.temp.toInt()}°C",
            fontSize = 64.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
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
        Text(text = "Ошибка:", fontSize = 24.sp, color = Color.Red)
        Text(text = message, modifier = Modifier.padding(top = 8.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingScreen() {
    LoadingScreen()
}

@Preview(showBackground = true)
@Composable
fun PreviewSuccessScreen() {
    val sampleWeather = WeatherDto(
        name = "Москва",
        coord = Coord(lon = 37.62, lat = 55.75),
        weather = listOf(
            Weather(
                id = 800,
                main = "Clear",
                description = "ясно",
                icon = "01d"
            )
        ),
        main = Main(
            temp = 25.0,
            feelsLike = 27.0,
            pressure = 1013,
            humidity = 60
        ),
        wind = Wind(
            speed = 5.0,
            deg = 180
        ),
        clouds = Clouds(all = 20),
        sys = Sys(
            country = "RU",
            sunrise = 1694156400,
            sunset = 1694203200
        )
    )

    WeatherInfoScreen(
        weather = sampleWeather
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewErrorScreen() {
    ErrorScreen(
        message = "Не удалось загрузить данные"
    )
}


