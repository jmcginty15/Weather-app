package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.models.onecall.DailyDTO
import com.example.weather.data.models.onecall.HourlyDTO
import com.example.weather.databinding.DailyItemBinding
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.roundToInt

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastAdapter.DailyForecastViewHolder>() {
    private var forecastList: List<DailyDTO> = listOf<DailyDTO>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder =
        DailyForecastViewHolder(
            DailyItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) = with(holder) {
        val forecastItem = forecastList[position]
        val iconUrl = itemView.context.getString(R.string.icon_url, forecastItem.weather[0].icon)
        loadData(forecastItem, iconUrl)
    }

    override fun getItemCount(): Int = forecastList.size

    class DailyForecastViewHolder(private val binding: DailyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun loadData(dailyItem: DailyDTO, iconUrl: String) = with(binding) {
            dayOfWeek.text = parseDay(dailyItem.unixTime)
            highTemp.text = kelvinToFahrenheit(dailyItem.temp.high).roundToInt().toString()
            lowTemp.text = kelvinToFahrenheit(dailyItem.temp.low).roundToInt().toString()
            Picasso.get().load(iconUrl).into(binding.icon)
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

        private fun kelvinToFahrenheit(K: Double): Double {
            return (K - 273.15) * 1.8 + 32.0
        }
    }

    fun addData(forecastList: List<DailyDTO>) {
        this.forecastList = forecastList
        notifyDataSetChanged()
    }
}