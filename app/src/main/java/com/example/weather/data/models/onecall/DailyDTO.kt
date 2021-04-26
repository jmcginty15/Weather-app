package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class DailyDTO(
    @SerializedName("dt") val unixTime: Long,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
    @SerializedName("moonrise") val moonrise: Long,
    @SerializedName("moonset") val moonset: Long,
    @SerializedName("moon_phase") val moonPhase: Double,
    @SerializedName("temp") val temp: TempDTO,
    @SerializedName("feels_like") val feelsLike: FeelsLikeDTO,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("dew_point") val dewPoint: Double,
    @SerializedName("wind_speed") val windSpeed: Double,
    @SerializedName("wind_gust") val windGust: Double,
    @SerializedName("wind_deg") val windDirection: Int,
    @SerializedName("weather") val weather: List<WeatherDTO>,
    @SerializedName("clouds") val cloudCoverage: Int,
    @SerializedName("pop") val precipitationProbability: Double,
    @SerializedName("rain") val rainVol: Double,
    @SerializedName("snow") val snowVol: Double,
    @SerializedName("uvi") val uvi: Double
)