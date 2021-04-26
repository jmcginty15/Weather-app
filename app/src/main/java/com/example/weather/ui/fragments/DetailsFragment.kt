package com.example.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.weather.R
import com.example.weather.databinding.DetailsFragmentBinding
import com.example.weather.ui.viewmodels.MainViewModel
import java.util.*
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class DetailsFragment : Fragment() {
    private lateinit var binding: DetailsFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailsFragmentBinding.inflate(inflater)
        binding.detailsLayout.setBackgroundColor(mViewModel.backgroundColor)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        val currentWeatherModel = mViewModel.currentWeatherModel.value!!
        val forecastModel = mViewModel.oneCallForecast.value!!

        description.text = resources.getString(
            R.string.description,
            forecastModel.current.weather[0].description,
            kelvinToFahrenheit(forecastModel.current.temp).roundToInt().toString(),
            kelvinToFahrenheit(forecastModel.daily[0].temp.high).roundToInt().toString(),
            kelvinToFahrenheit(forecastModel.daily[0].temp.low).roundToInt().toString()
        )
        cityName.text = currentWeatherModel.cityName
        mainWeather.text = currentWeatherModel.mainWeather
        latitudeValue.text = formatLat(forecastModel.latitude)
        longitudeValue.text = formatLon(forecastModel.longitude)
        sunriseValue.text = parseTime(forecastModel.current.sunrise)
        sunsetValue.text = parseTime(forecastModel.current.sunset)
        moonriseValue.text = parseTime(forecastModel.daily[0].moonrise)
        moonsetValue.text = parseTime(forecastModel.daily[0].moonset)
        humidityValue.text =
            resources.getString(R.string.percentage, forecastModel.current.humidity.toString())
        feelsLikeValue.text = resources.getString(
            R.string.degrees,
            kelvinToFahrenheit(forecastModel.current.feelsLike).roundToInt().toString()
        )
        windValue.text = parseWindSpeedAndDirection(
            forecastModel.current.windSpeed,
            forecastModel.current.windDirection
        )
        gustsValue.text = parseWindSpeed(forecastModel.daily[0].windGust)
        dewPointValue.text = resources.getString(
            R.string.degrees,
            kelvinToFahrenheit(forecastModel.current.dewPoint).roundToInt().toString()
        )
        pressureValue.text =
            resources.getString(R.string.in_hg, getInHg(forecastModel.current.pressure.toDouble()))
        visibilityValue.text = resources.getString(
            R.string.miles,
            getMiles(forecastModel.current.visibility).toString()
        )
        uvIndexValue.text = forecastModel.current.uvi.toString()
        cloudCoverageValue.text =
            resources.getString(R.string.percentage, forecastModel.current.cloudCoverage.toString())
        chanceOfPrecipitationValue.text = resources.getString(
            R.string.percentage,
            forecastModel.daily[0].precipitationProbability.roundToInt().toString()
        )

        backButton.setOnClickListener { navigateBack() }
    }

    private fun navigateBack() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.details_fragment_pop)
    }

    private fun kelvinToFahrenheit(K: Double): Double {
        return (K - 273.15) * 1.8 + 32.0
    }

    private fun formatLat(latitude: Double): String {
        val latDir = if (latitude < 0) "S" else "N"
        return formatDMS(latitude, latDir)
    }

    private fun formatLon(longitude: Double): String {
        val longDir = if (longitude < 0) "W" else "E"
        return formatDMS(longitude, longDir)
    }

    private fun formatDMS(decimalValue: Double, direction: String): String {
        val degrees = floor(abs(decimalValue))
        val leftover = (abs(decimalValue) - degrees) * 60.0
        val minutes = floor(leftover)
        val seconds = (leftover - minutes) * 60.0

        return resources.getString(
            R.string.dms,
            degrees.roundToInt().toString(),
            minutes.roundToInt().toString(),
            seconds.roundToInt().toString(),
            direction
        )
    }

    private fun parseTime(unixTime: Long): String {
        val date = Date(unixTime * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = date
        var hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
        if (hour.length == 1) hour = "0$hour"
        var minute = calendar.get(Calendar.MINUTE).toString()
        if (minute.length == 1) minute = "0$minute"
        return "$hour:$minute"
    }

    private fun parseWindSpeedAndDirection(speed: Double, direction: Int): String {
        return "${parseWindDirection(direction)} ${getMPH(speed)} mph"
    }

    private fun parseWindSpeed(speed: Double): String {
        return "${getMPH(speed)} mph"
    }

    private fun parseWindDirection(direction: Int): String {
        return when {
            direction < 11.25 || direction > 348.75 -> "N"
            direction > 11.25 && direction < 33.75 -> "NNE"
            direction > 33.75 && direction < 56.25 -> "NE"
            direction > 56.25 && direction < 78.75 -> "ENE"
            direction > 78.75 && direction < 101.25 -> "E"
            direction > 101.25 && direction < 123.75 -> "ESE"
            direction > 123.75 && direction < 146.25 -> "SE"
            direction > 146.25 && direction < 168.75 -> "SSE"
            direction > 168.75 && direction < 191.25 -> "S"
            direction > 191.25 && direction < 213.75 -> "SSW"
            direction > 213.75 && direction < 236.25 -> "SW"
            direction > 236.25 && direction < 258.75 -> "WSW"
            direction > 258.75 && direction < 281.25 -> "W"
            direction > 281.25 && direction < 303.75 -> "WNW"
            direction > 303.75 && direction < 326.25 -> "NW"
            direction > 326.25 && direction < 348.75 -> "NNW"
            else -> "You done goofed"
        }
    }

    private fun getMPH(speed: Double): Int {
        return (speed * 2.2369362920544025).roundToInt()
    }

    private fun getInHg(pressure: Double): String {
        return "%.1f".format(pressure * 0.02953)
    }

    private fun getMiles(distance: Long): Int {
        return (distance * 0.0006213712).roundToInt()
    }
}