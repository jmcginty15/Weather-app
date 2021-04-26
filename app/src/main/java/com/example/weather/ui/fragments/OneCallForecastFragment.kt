package com.example.weather.ui.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.data.models.onecall.OneCallDTO
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.ui.adapters.DailyForecastAdapter
import com.example.weather.ui.adapters.HourlyForecastAdapter
import com.example.weather.ui.viewmodels.CurrentWeatherModel
import com.example.weather.ui.viewmodels.MainViewModel
import java.text.DecimalFormat
import java.util.*
import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.roundToInt

class OneCallForecastFragment : Fragment() {
    private lateinit var binding: ForecastFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()
    private val hourlyForecastAdapter: HourlyForecastAdapter by lazy {
        HourlyForecastAdapter()
    }
    private val dailyForecastAdapter: DailyForecastAdapter by lazy {
        DailyForecastAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ForecastFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.hourlyRecycler.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = hourlyForecastAdapter
        }

        binding.dailyRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = dailyForecastAdapter
        }

        if (mViewModel.dataReady) loadViewData(mViewModel.oneCallForecast.value!!)
        else mViewModel.oneCallForecast.observe(
            viewLifecycleOwner,
            { result -> loadViewData(result) })

        binding.changeLocationButton.setOnClickListener { navigateToCitySelect() }
    }

    private fun getCityName(latitude: Double, longitude: Double): String {
        val formatter = DecimalFormat()
        formatter.maximumFractionDigits = 3
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = try {
            geocoder.getFromLocation(
                formatter.format(latitude).toDouble(),
                formatter.format(longitude).toDouble(),
                1
            )[0]
        } catch (e: Throwable) {
            null
        }

        println(formatLatLon(latitude, longitude))

        return if (address == null) formatLatLon(latitude, longitude)
        else {
            if (address.locality == null) {
                if (address.subAdminArea == null) {
                    if (address.adminArea == null) address.countryName
                    else address.adminArea
                } else address.subAdminArea
            } else address.locality
        }
    }

    private fun formatLatLon(latitude: Double, longitude: Double): String {
        val latDir = if (latitude < 0) "S" else "N"
        val longDir = if (longitude < 0) "W" else "E"
        return "${formatDMS(latitude, latDir)}\n${formatDMS(longitude, longDir)}"
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

    private fun parseDay(unixTime: Long): String {
        val date = Date(unixTime * 1000)
        val calendar = Calendar.getInstance()
        calendar.time = date

        return when (calendar.get(Calendar.DAY_OF_WEEK)) {
            Calendar.SUNDAY -> "Sunday"
            Calendar.MONDAY -> "Monday"
            Calendar.TUESDAY -> "Tuesday"
            Calendar.WEDNESDAY -> "Wednesday"
            Calendar.THURSDAY -> "Thursday"
            Calendar.FRIDAY -> "Friday"
            Calendar.SATURDAY -> "Saturday"
            else -> "You done goofed"
        }
    }

    private fun setBackGroundColor(
        mainWeather: String,
        unixTime: Long,
        sunriseUnixTime: Long,
        sunsetUnixTime: Long
    ) {
        val timeOfDay = timeOfDay(unixTime, sunriseUnixTime, sunsetUnixTime)
        val stormy = listOf("Thunderstorm", "Drizzle", "Rain", "Squall", "Tornado")
        val cloudy =
            listOf("Clouds", "Snow", "Mist", "Smoke", "Haze", "Dust", "Fog", "Sand", "Dust", "Ash")
        var backgroundColor = 0
        if (timeOfDay == "d") {
            backgroundColor = when (mainWeather) {
                "Clear" -> R.color.sunny
                in stormy -> R.color.rainy
                in cloudy -> R.color.cloudy
                else -> 0
            }
        } else if (timeOfDay == "n") {
            backgroundColor =
                if (mainWeather == "Clear") R.color.night_clear else R.color.night_cloudy
        }
        val resolvedColor = ContextCompat.getColor(requireContext(), backgroundColor)
        mViewModel.backgroundColor = resolvedColor
        binding.forecastLayout.setBackgroundColor(resolvedColor)
    }

    private fun timeOfDay(unixTime: Long, sunriseUnixTime: Long, sunsetUnixTime: Long): String {
        val current = Date(unixTime * 1000)
        val sunrise = Date(sunriseUnixTime * 1000)
        val sunset = Date(sunsetUnixTime * 1000)

        return if (current > sunrise && current < sunset) "d" else "n"
    }

    private fun navigateToCitySelect() {
        mViewModel.dataReady = false
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.action_forecast_fragment_to_city_select_fragment)
    }

    private fun loadViewData(result: OneCallDTO) {
        val model = CurrentWeatherModel(
            getCityName(result.latitude, result.longitude),
            result.current.weather[0].main,
            result.current.temp,
            result.daily[0].temp.high,
            result.daily[0].temp.low,
            parseDay(result.current.unixTime)
        )
        mViewModel.setCurrentWeatherModel(model)

        hourlyForecastAdapter.addData(result.hourly)
        dailyForecastAdapter.addData(result.daily)
        setBackGroundColor(
            result.current.weather[0].main,
            result.current.unixTime,
            result.current.sunrise,
            result.current.sunset
        )
    }
}
