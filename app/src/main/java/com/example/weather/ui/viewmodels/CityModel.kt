package com.example.weather.ui.viewmodels

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName(value = "id") val id: Double,
    @SerializedName(value = "name") val name: String,
    @SerializedName(value = "state") val state: String,
    @SerializedName(value = "country") val country: String,
    @SerializedName(value = "coord") val coordinates: CoordinatesModel
)