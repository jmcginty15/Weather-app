package com.example.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.weather.R
import com.example.weather.databinding.CitySelectFragmentBinding
import com.example.weather.ui.CITY_LIST
import com.example.weather.ui.MainActivity
import com.example.weather.ui.viewmodels.CityLookupModel
import com.example.weather.ui.viewmodels.CityModel
import com.example.weather.ui.viewmodels.CoordinatesModel
import com.example.weather.ui.viewmodels.MainViewModel

class CitySelectFragment : Fragment() {
    private lateinit var binding: CitySelectFragmentBinding
    private val mViewModel: MainViewModel by activityViewModels()
    private var goBack = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CitySelectFragmentBinding.inflate(inflater)
        binding.citySelectLayout.setBackgroundColor(mViewModel.backgroundColor)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel.oneCallForecast.observe(viewLifecycleOwner, {
            if (goBack) navigateBack()
            else goBack = true
        })

        binding.backButton.setOnClickListener { navigateBack() }
        binding.currentLocationButton.setOnClickListener { getCurrentLocation() }
        binding.enterLocationButton.setOnClickListener { findCity() }

        val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, getCityStrings())
        binding.citySelect.setAdapter(adapter)
    }

    private fun navigateBack() {
        val navController = NavHostFragment.findNavController(this)
        navController.navigate(R.id.city_select_fragment_pop)
    }

    private fun getCurrentLocation() {
        setButtonsUnclickable()
        (activity as MainActivity).getLastLocation()
    }

    private fun setButtonsUnclickable() {
        for (button in listOf(
            binding.backButton,
            binding.currentLocationButton,
            binding.enterLocationButton
        )) {
            button.isClickable = false
            button.setStrokeColorResource(R.color.white_transparent)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_transparent))
        }
    }

    private fun getCityStrings(): List<String> {
        val strings = mutableListOf<String>()
        for (city in CITY_LIST) strings.add(parseCityString(city))
        return strings
    }

    private fun parseCityString(city: CityModel): String {
        var string = city.name
        if (city.state != "") string += ", ${city.state}"
        if (city.country != "") string += ", ${city.country}"
        return string
    }

    private fun getCityLookupModel(cityString: String): CityLookupModel {
        val cityName: String
        val stateId: String
        val countryId: String

        var commaIndex = cityString.lastIndexOf(",")
        countryId = cityString.slice(commaIndex + 2 until cityString.length)
        val newCityString = cityString.slice(0 until commaIndex)

        commaIndex = newCityString.lastIndexOf(",")
        if (commaIndex == -1) {
            stateId = ""
            cityName = newCityString
        } else {
            stateId = newCityString.slice(commaIndex + 2 until newCityString.length)
            cityName = newCityString.slice(0 until commaIndex)
        }

        return CityLookupModel(cityName, stateId, countryId)
    }

    private fun lookUpCoordinates(
        cityName: String,
        stateId: String,
        countryId: String
    ): CoordinatesModel {
        for (city in CITY_LIST) if (city.name == cityName && city.state == stateId && city.country == countryId) return city.coordinates
        return CoordinatesModel(0.0, 0.0)
    }

    private fun findCity() {
        val cityString = binding.citySelect.text.toString()
        val model = getCityLookupModel(cityString)
        val coordinates = lookUpCoordinates(model.cityName, model.stateId, model.countryId)

        if (coordinates.latitude != 0.0 || coordinates.longitude != 0.0) getForecast(coordinates)
        else binding.cityNotFound.text = resources.getString(R.string.city_not_found)
    }

    private fun getForecast(coordinates: CoordinatesModel) {
        setButtonsUnclickable()
        mViewModel.getOneCallForecast(coordinates.latitude, coordinates.longitude)
    }
}