package com.example.weather.ui.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.databinding.ForecastFragmentBinding
import com.example.weather.ui.adapters.DailyForecastAdapter
import com.example.weather.ui.adapters.HourlyForecastAdapter
import com.example.weather.ui.viewmodels.CurrentWeatherModel
import com.example.weather.ui.viewmodels.MainViewModel
import java.text.DecimalFormat
import java.util.*

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

        mViewModel.oneCallForecast.observe(viewLifecycleOwner, { result ->
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
        })
    }

    private fun getCityName(latitude: Double, longitude: Double): String {
        val formatter = DecimalFormat()
        formatter.maximumFractionDigits = 3
        val geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geocoder.getFromLocation(
            formatter.format(latitude).toDouble(),
            formatter.format(longitude).toDouble(),
            1
        )[0]
        return address.locality
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
}