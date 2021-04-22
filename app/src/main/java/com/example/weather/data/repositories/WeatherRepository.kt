package com.example.weather.data.repositories

import com.example.weather.data.models.fivedaythreehour.FiveDayThreeHourDTO
import com.example.weather.data.models.onecall.OneCallDTO
import com.example.weather.data.remote.WeatherManager
import io.reactivex.Single

class WeatherRepository {
    fun getFiveDayThreeHourForecast(cityId: Long): Single<FiveDayThreeHourDTO> =
        WeatherManager().getFiveDayThreeHourForecast(cityId)

    fun getOneCallForecast(latitude: Double, longitude: Double): Single<OneCallDTO> =
        WeatherManager().getOneCallForecast(latitude, longitude)
}