package com.example.weather.data.models

import com.google.gson.annotations.SerializedName

class ForecastDTO(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("dt_txt") val textTime: String,
    @SerializedName("main") val main: MainDTO,
    @SerializedName("weather") val weather: List<WeatherDTO>,
    @SerializedName("clouds") val clouds: CloudsDTO,
    @SerializedName("wind") val wind: WindDTO,
    @SerializedName("visibility") val visibility: Long,
    @SerializedName("pop") val precipitationProbability: Double,
    @SerializedName("rain") val rain: RainDTO?,
    @SerializedName("snow") val snow: SnowDTO?,
    @SerializedName("sys") val sys: SysDTO
)