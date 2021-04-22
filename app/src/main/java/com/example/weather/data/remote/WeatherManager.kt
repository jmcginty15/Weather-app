package com.example.weather.data.remote

import com.example.weather.data.models.fivedaythreehour.FiveDayThreeHourDTO
import com.example.weather.data.models.onecall.OneCallDTO
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

class WeatherManager {
    private val service: WeatherService
    private val retrofit = RetrofitService().providesRetrofitService()

    init {
        service = retrofit.create(WeatherService::class.java)
    }

    fun getFiveDayThreeHourForecast(cityId: Long) =
        service.getFiveDayThreeHourForecast(cityId.toString())

    fun getOneCallForecast(latitude: Double, longitude: Double) =
        service.getOneCallForecast(latitude.toString(), longitude.toString())

    interface WeatherService {
        @GET("forecast")
        fun getFiveDayThreeHourForecast(
            @Query("id") cityId: String,
            @Query("appid") key: String = API_KEY
        ): Single<FiveDayThreeHourDTO>

        @GET("onecall")
        fun getOneCallForecast(
            @Query("lat") latitude: String,
            @Query("lon") longitude: String,
            @Query("appid") key: String = API_KEY
        ): Single<OneCallDTO>
    }

    companion object {
        const val API_KEY = "b5c8836b74913102e453bd84c0e1117b"
    }
}