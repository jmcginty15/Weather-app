package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class FeelsLikeDTO(
    @SerializedName("day") val day: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val evening: Double,
    @SerializedName("morn") val morning: Double
)