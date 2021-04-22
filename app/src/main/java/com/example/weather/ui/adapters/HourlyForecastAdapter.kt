package com.example.weather.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.models.onecall.HourlyDTO
import com.example.weather.databinding.HourlyItemBinding
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.math.roundToInt

class HourlyForecastAdapter :
    RecyclerView.Adapter<HourlyForecastAdapter.HourlyForecastViewHolder>() {
    private var forecastList: List<HourlyDTO> = listOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HourlyForecastViewHolder = HourlyForecastViewHolder(
        HourlyItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) = with(holder) {
        val forecastItem = forecastList[position]
        val nowString = if (position == 0) itemView.context.getString(R.string.now) else null
        val iconUrl = itemView.context.getString(R.string.icon_url, forecastItem.weather[0].icon)
        loadData(forecastItem, nowString, iconUrl)
    }

    override fun getItemCount(): Int = forecastList.size

    class HourlyForecastViewHolder(private val binding: HourlyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun loadData(hourlyItem: HourlyDTO, nowString: String?, iconUrl: String) = with(binding) {
            time.text = nowString ?: parseHour(hourlyItem.unixTime)
            temp.text = kelvinToFahrenheit(hourlyItem.temp).roundToInt().toString()
            Picasso.get().load(iconUrl).into(binding.icon)
        }

        private fun parseHour(unixTime: Long): String {
            val date = Date(unixTime * 1000)
            val calendar = Calendar.getInstance()
            calendar.time = date
            var hour = calendar.get(Calendar.HOUR_OF_DAY).toString()
            if (hour.length == 1) hour = "0$hour"
            return hour
        }

        private fun kelvinToFahrenheit(K: Double): Double {
            return (K - 273.15) * 1.8 + 32.0
        }
    }

    fun addData(forecastList: List<HourlyDTO>) {
        this.forecastList = forecastList.subList(0, 24)
        notifyDataSetChanged()
    }
}