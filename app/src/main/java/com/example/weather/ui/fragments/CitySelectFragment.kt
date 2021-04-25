package com.example.weather.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.weather.R
import com.example.weather.databinding.CitySelectFragmentBinding
import com.example.weather.ui.MainActivity
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
        for (button in listOf(binding.backButton, binding.currentLocationButton)) {
            button.isClickable = false
            button.setStrokeColorResource(R.color.white_transparent)
            button.setTextColor(ContextCompat.getColor(requireContext(), R.color.white_transparent))
        }
    }
}