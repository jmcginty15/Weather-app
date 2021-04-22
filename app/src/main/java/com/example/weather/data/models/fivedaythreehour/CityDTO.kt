package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class CityDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("coord") val coordinates: CoordinatesDTO,
    @SerializedName("country") val country: String,
    @SerializedName("timezone") val timezone: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long
)