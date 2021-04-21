package com.example.weather.data.models

import com.google.gson.annotations.SerializedName

class ResultsDTO(
        @SerializedName("list") val forecast: List<ForecastDTO>,
        @SerializedName("city") val city: CityDTO
)