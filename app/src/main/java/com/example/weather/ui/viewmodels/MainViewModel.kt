package com.example.weather.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weather.data.models.fivedaythreehour.FiveDayThreeHourDTO
import com.example.weather.data.models.onecall.OneCallDTO
import com.example.weather.data.repositories.WeatherRepository
import com.example.weather.ui.CITY_LIST
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val disposable = CompositeDisposable()
    private val weatherRepository = WeatherRepository()
    var backgroundColor = 0
    var dataReady = false

    val fiveDayThreeHourForecast: LiveData<FiveDayThreeHourDTO>
        get() = _fiveDayThreeHourForecast

    private val _fiveDayThreeHourForecast: MutableLiveData<FiveDayThreeHourDTO> by lazy {
        MutableLiveData<FiveDayThreeHourDTO>()
    }

    val oneCallForecast: LiveData<OneCallDTO>
        get() = _oneCallForecast

    private val _oneCallForecast: MutableLiveData<OneCallDTO> by lazy {
        MutableLiveData<OneCallDTO>()
    }

    val currentWeatherModel: LiveData<CurrentWeatherModel>
        get() = _currentWeatherModel

    private val _currentWeatherModel: MutableLiveData<CurrentWeatherModel> by lazy {
        MutableLiveData<CurrentWeatherModel>()
    }

    private fun getFiveDayThreeHourForecast(cityId: Long) {
        disposable.add(
            weatherRepository.getFiveDayThreeHourForecast(cityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetForecastSuccess, this::onGetForecastError)
        )
    }

    fun getOneCallForecast(latitude: Double, longitude: Double) {
        disposable.add(
            weatherRepository.getOneCallForecast(latitude, longitude).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onGetForecastSuccess, this::onGetForecastError)
        )
    }

    private fun onGetForecastSuccess(response: FiveDayThreeHourDTO) {
        _fiveDayThreeHourForecast.value = response
    }

    private fun onGetForecastSuccess(response: OneCallDTO) {
        _oneCallForecast.value = response
    }

    private fun onGetForecastError(e: Throwable) {

    }

    fun setCurrentWeatherModel(currentWeatherModel: CurrentWeatherModel) {
        _currentWeatherModel.value = currentWeatherModel
    }

    private fun lookUpCoordinates(
        cityName: String,
        stateId: String,
        countryId: String
    ): CoordinatesModel {
        for (city in CITY_LIST) if (city.name == cityName && city.state == stateId && city.country == countryId) return city.coordinates
        return CoordinatesModel(0.0, 0.0)
    }
}