package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class MinutelyDTO(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("precipitation") val precipitation: Double
)