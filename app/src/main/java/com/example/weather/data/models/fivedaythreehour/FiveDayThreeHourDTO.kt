package com.example.weather.data.models.fivedaythreehour

import com.google.gson.annotations.SerializedName

class FiveDayThreeHourDTO(
        @SerializedName("list") val forecast: List<ForecastDTO>,
        @SerializedName("city") val city: CityDTO
)