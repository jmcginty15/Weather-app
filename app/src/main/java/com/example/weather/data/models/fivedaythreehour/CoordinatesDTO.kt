package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class CoordinatesDTO(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double
)