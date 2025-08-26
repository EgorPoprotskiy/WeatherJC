package com.egorpoprotskiy.weatherjc

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
//1
@Composable
fun WeatherApp(
    modifier: Modifier = Modifier
) {
    Text(
        text = "Weather App",
        modifier = modifier
            .fillMaxSize()
    )
}

@Preview
@Composable
fun WeatherAppPreview() {
    MaterialTheme {
        WeatherApp()
    }
}