package com.example.weather.data.models

import com.google.gson.annotations.SerializedName

class CloudsDTO(
    @SerializedName("all") val coverage: Int
)