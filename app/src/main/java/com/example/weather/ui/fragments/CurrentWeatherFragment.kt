package com.example.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.weather.R
import com.example.weather.databinding.CurrentWeatherFragmentBinding
import com.example.weather.ui.viewmodels.MainViewModel
import kotlin.math.roundToInt

class CurrentWeatherFragment : Fragment() {
    private lateinit var binding: CurrentWeatherFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CurrentWeatherFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewModel.currentWeatherModel.observe(viewLifecycleOwner, { model ->
            binding.cityName.text = model.cityName
            binding.mainWeather.text = model.mainWeather
            binding.dayOfWeek.text = model.dayOfWeek
            binding.temp.text =
                resources.getString(
                    R.string.degrees,
                    kelvinToFahrenheit(model.temp).roundToInt().toString()
                )
            binding.highTemp.text = kelvinToFahrenheit(model.hiTemp).roundToInt().toString()
            binding.lowTemp.text = kelvinToFahrenheit(model.loTemp).roundToInt().toString()
        })
    }

    private fun kelvinToFahrenheit(K: Double): Double {
        return (K - 273.15) * 1.8 + 32.0
    }
}