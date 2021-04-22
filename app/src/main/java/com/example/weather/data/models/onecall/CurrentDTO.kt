package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class CurrentDTO(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("clouds") val cloudCoverage: Int,
    @SerializedName("visibility") val visibility: Long,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDirection: Int,
    @SerializedName("weather") val weather: List<WeatherDTO>,
    @SerializedName("rain") val rain: RainDTO,
    @SerializedName("snow") val snow: SnowDTO
)