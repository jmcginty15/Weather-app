package com.example.weather.data.models

import com.google.gson.annotations.SerializedName

class MainDTO(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val low: Double,
    @SerializedName("temp_max") val high: Double,
    @SerializedName("pressure") val pressure: Long,
    @SerializedName("sea_level") val seaLevel: Long,
    @SerializedName("grnd_level") val groundLevel: Long,
    @SerializedName("humidity") val humidity: Int
)