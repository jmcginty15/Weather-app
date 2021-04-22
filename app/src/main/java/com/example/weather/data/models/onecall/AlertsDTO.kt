package com.example.weather.data.models.onecall

import com.google.gson.annotations.SerializedName

class AlertsDTO(
    @SerializedName("sender_name") val senderName: String,
    @SerializedName("event") val event: String,
    @SerializedName("start") val start: Long,
    @SerializedName("end") val end: Long,
    @SerializedName("description") val description: String
)