package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class TempDTO(
    @SerializedName("day") val day: Double,
    @SerializedName("min") val low: Double,
    @SerializedName("max") val high: Double,
    @SerializedName("night") val night: Double,
    @SerializedName("eve") val evening: Double,
    @SerializedName("morn") val morning: Double
)