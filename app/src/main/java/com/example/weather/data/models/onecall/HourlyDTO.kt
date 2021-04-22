package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class HourlyDTO(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("uvi") val uvi: Double,
    @SerializedName("clouds") val cloudCoverage: Int,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_deg") val windDirection: Int,
    @SerializedName("wind_gust") val windGust: Double,
    @SerializedName("weather") val weather: List<WeatherDTO>,
    @SerializedName("pop") val precipitationProbability: Double
)