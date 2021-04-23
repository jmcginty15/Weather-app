package com.example.weather.ui.viewmodels

import com.google.gson.annotations.SerializedName

data class CoordinatesModel(
    @SerializedName(value = "lat") val latitude: Double,
    @SerializedName(value = "lon") val longitude: Double
)