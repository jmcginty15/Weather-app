package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class OneCallDTO(
    @SerializedName("lat") val latitude: Double,
    @SerializedName("lon") val longitude: Double,
    @SerializedName("timezone") val timeZone: String,
    @SerializedName("timezone_offset") val timeZoneOffset: Long,
    @SerializedName("current") val current: CurrentDTO,
    @SerializedName("minutely") val minutely: List<MinutelyDTO>,
    @SerializedName("hourly") val hourly: List<HourlyDTO>,
    @SerializedName("daily") val daily: List<DailyDTO>,
    @SerializedName("alerts") val alerts: List<AlertsDTO>
)