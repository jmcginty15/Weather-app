package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class RainDTO(
    @SerializedName("3h") val rainVol3h: Double
)