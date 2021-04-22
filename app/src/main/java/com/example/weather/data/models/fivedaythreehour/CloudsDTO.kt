package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class CloudsDTO(
    @SerializedName("all") val coverage: Int
)