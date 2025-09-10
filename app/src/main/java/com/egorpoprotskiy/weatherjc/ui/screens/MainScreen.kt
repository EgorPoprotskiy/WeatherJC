package com.egorpoprotskiy.weatherjc.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.egorpoprotskiy.weatherjc.data.City
import com.egorpoprotskiy.weatherjc.data.Clouds
import com.egorpoprotskiy.weatherjc.data.Coord
import com.egorpoprotskiy.weatherjc.data.ForecastDto
import com.egorpoprotskiy.weatherjc.data.ForecastItem
import com.egorpoprotskiy.weatherjc.data.ForecastSys
import com.egorpoprotskiy.weatherjc.data.Main
import com.egorpoprotskiy.weatherjc.data.Sys
import com.egorpoprotskiy.weatherjc.data.Weather
import com.egorpoprotskiy.weatherjc.data.WeatherDto
import com.egorpoprotskiy.weatherjc.data.Wind
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.ForecastState
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherState
import com.egorpoprotskiy.weatherjc.presentation.viewmodel.WeatherViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.time.Duration.Companion.milliseconds
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//11,5
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    // Состояние для хранения текста в поле поиска
    var city by remember { mutableStateOf("") }
    // Собираем состояние для текущего прогнозаиз ViewModel. Compose будет перерисовывать UI при каждом изменении.
    val weatherState by viewModel.weatherState.collectAsStateWithLifecycle()
    // Собираем состояние для 5-дневного прогноза из ViewModel. Compose будет перерисовывать UI при каждом изменении.
    val forecastState by viewModel.forecastState.collectAsStateWithLifecycle()
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
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }
                is WeatherState.Success -> {
                    // Используем Column, чтобы оба компонента делили пространство
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Показываем данные о погоде
                        WeatherInfoScreen(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            weather = (weatherState as WeatherState.Success).weather
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        // Отдельно обрабатываем состояние прогноза
                        when (forecastState) {
                            is ForecastState.Loading -> {
                                LoadingScreen(modifier = Modifier.fillMaxWidth().height(200.dp))
                            }

                            is ForecastState.Success -> {
                                ForecastGridInfoScreen(
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    forecast = (forecastState as ForecastState.Success).forecast,
                                    contentPadding = contentPadding
                                )
                            }

                            is ForecastState.Error -> {
                                ErrorScreen(
                                    modifier = Modifier.fillMaxWidth().height(200.dp),
                                    message = (forecastState as ForecastState.Error).message
                                )
                            }
                        }
                    }
                }
                is WeatherState.Error -> {
                    // Показываем сообщение об ошибке
                    ErrorScreen(
                        modifier = Modifier.fillMaxSize(),
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

// Компонент для отображения информации о текущей погоде
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

// Компонент для отображения информации о 5-дневной погоде
@Composable
fun ForecastGridInfoScreen(
    modifier: Modifier = Modifier,
    forecast: ForecastDto,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(
            items = forecast.list,
            key = { item -> item.dt }
        ) { forecastItem ->
            ForecastCardInfoScreen(
                forecastItem = forecastItem,
//                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

//Отображение одной карточки в 5-дневном прогнозе
@Composable
fun ForecastCardInfoScreen(
    modifier: Modifier = Modifier,
    forecastItem: ForecastItem
) {
    val date = Date(forecastItem.dt.toLong() * 1000)
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    val formattedTime = formatter.format(date)
    Card(
        modifier = modifier.height(150.dp).fillMaxWidth(0.3f),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(text = formattedTime, fontSize = 14.sp)
            Text(
                text = "${forecastItem.main.temp.toInt()}°C",
                fontSize = 24.sp
            )
            Text(
                text = forecastItem.weather.firstOrNull()?.description ?: "Нет данных",
                fontSize = 12.sp
            )
        }
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
fun PreviewForecastCardInfoScreen() {
    val sampleWeather = ForecastItem(
                dt = 10,
                main = Main(
                    temp = 25.0,
                    feelsLike = 27.0,
                    pressure = 1013,
                    humidity = 60
                ),
                weather = listOf(
                    Weather(
                        id = 800,
                        main = "Clear",
                        description = "ясно",
                        icon = "01d"
                    )
                ),
                clouds = Clouds(all = 20),
                wind = Wind(
                    speed = 5.0,
                    deg = 180
                ),
                visibility = 10,
                pop = 10.5,
                sys = ForecastSys(
                    ""
                ),
                dt_txt = ""
            )
    ForecastCardInfoScreen(
        forecastItem = sampleWeather
    )
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


