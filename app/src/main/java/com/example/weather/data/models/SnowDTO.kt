package com.example.weather.data.models

import com.google.gson.annotations.SerializedName

class SnowDTO(
    @SerializedName("3h") val snowVol3h: Double
)