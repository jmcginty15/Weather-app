package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class WindDTO(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val direction: Int,
    @SerializedName("gust") val gust: Double
)