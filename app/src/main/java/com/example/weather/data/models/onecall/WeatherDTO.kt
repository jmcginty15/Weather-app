package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class WeatherDTO(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String
)