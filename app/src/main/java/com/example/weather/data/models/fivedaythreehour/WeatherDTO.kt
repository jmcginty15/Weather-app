package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class WeatherDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)