package com.example.weather.ui.viewmodels

data class CurrentWeatherModel(
    val cityName: String,
    val mainWeather: String,
    val temp: Double,
    val hiTemp: Double,
    val loTemp: Double,
    val dayOfWeek: String
)