package com.example.weather.data.models

import com.google.gson.annotations.SerializedName

class SysDTO(
    @SerializedName("pod") val partOfDay: Char
)