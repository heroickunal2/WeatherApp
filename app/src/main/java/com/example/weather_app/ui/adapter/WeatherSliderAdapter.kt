package com.example.weather_app.ui.adapter

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.weather_app.model.ColorGradient
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.util.Util
import com.example.weatherapp.R
import com.smarteist.autoimageslider.SliderViewAdapter
import kotlinx.android.synthetic.main.item_weather.view.*
import timber.log.Timber


class WeatherSliderAdapter(
    val fragmentActivity: FragmentActivity,
    private val weatherResponseList: List<WeatherResponse>
) :
    SliderViewAdapter<WeatherSliderAdapter.SliderAdapterVH>() {

    var currentDay = ""
    var nextDay = 1

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val inflate = LayoutInflater.from(parent?.context)
            .inflate(R.layout.item_weather, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(holder: SliderAdapterVH, position: Int) {
        Timber.d("onBindViewHolder ${weatherResponseList}")
        holder.textViewWeatherStatus.text =
            weatherResponseList[position].list[0].weather[0].main
        holder.textViewLocation.text =
            weatherResponseList[position].city.name + ", " + weatherResponseList[position].city.country
        holder.textViewTemp.text =
            weatherResponseList[position].list[0].main.temp.toInt().toString() + "\u2103"
        holder.textViewHighTemp.text =
            "H " + weatherResponseList[position].list[0].main.temp_max.toInt().toString()
        holder.textViewLowTemp.text =
            "L " + weatherResponseList[position].list[0].main.temp_min.toInt().toString()

        holder.itemView.imageView_weather_status.setImageResource(getWeatherImage(weatherResponseList[position].list[0].weather[0].description))

        currentDay = Util.parseDateToDay(weatherResponseList[position].list[0].dt_txt)
        nextDay = 1

        holder.tvDay1.text = findNextDay(weatherResponseList[position])
        holder.tvDay2.text = findNextDay(weatherResponseList[position])
        holder.tvDay3.text = findNextDay(weatherResponseList[position])


        holder.ivDay1Weather.setImageResource(getWeatherImage(weatherResponseList[position].list[1].weather[0].description))
        holder.ivDay2Weather.setImageResource(getWeatherImage(weatherResponseList[position].list[2].weather[0].description))
        holder.ivDay3Weather.setImageResource(getWeatherImage(weatherResponseList[position].list[3].weather[0].description))

        val colorGradient = getWeatherColor(weatherResponseList[position].list[0].weather[0].description)
        val topColor = colorGradient.topColor
        val bottomColor = colorGradient.bottomColor
        val colors = intArrayOf(
            bottomColor,
            topColor
        )
        val gd = GradientDrawable(
            GradientDrawable.Orientation.TOP_BOTTOM, colors)
        gd.cornerRadius = 0f

        holder.itemView.cl_main_layout.setBackgroundDrawable(gd)
    }

    private fun findNextDay(weatherResponseList: WeatherResponse): CharSequence? {
        while (currentDay == Util.parseDateToDay(weatherResponseList.list[nextDay].dt_txt)) {
            nextDay++
        }
        currentDay = Util.parseDateToDay(weatherResponseList.list[nextDay].dt_txt)
        Timber.d("currentDay ${currentDay}")
        return currentDay
    }


    override fun getCount(): Int {
        //slider view count could be dynamic size
        return weatherResponseList.size
    }

    fun getWeatherImage(description: String): Int {
        return when (description) {
            "light rain" -> R.drawable.ligthrain
            "scattered clouds" -> R.drawable.scatteredcloud
            "sky is clear" -> R.drawable.clearsky
            "heavy intensity rain" -> R.drawable.heavyrain
            "few clouds" -> R.drawable.fewclouds
            "moderate rain" -> R.drawable.moderaterain
            "broken clouds" -> R.drawable.brokenclouds
            "overcast clouds" -> R.drawable.overcastclouds
            "light shower snow" -> R.drawable.lightshowerrain
            "snow" -> R.drawable.ic_snow
            else -> R.drawable.clearsky
        }
    }

    fun getColor(color:Int):Int
    {
        return ContextCompat.getColor(fragmentActivity, color)
    }

    fun getWeatherColor(description: String): ColorGradient {
        return when (description) {
            "light rain" -> ColorGradient(getColor(R.color.black),getColor(R.color.lightBlack))
            "scattered clouds" -> ColorGradient(getColor(R.color.blue),getColor(R.color.lightBlue))
            "sky is clear" -> ColorGradient(getColor(R.color.orange),getColor(R.color.orange))
            "heavy intensity rain" -> ColorGradient(getColor(R.color.blue),getColor(R.color.lightBlue))
            "few clouds" -> ColorGradient(getColor(R.color.orange),getColor(R.color.white))
            "moderate rain" -> ColorGradient(getColor(R.color.blue),getColor(R.color.lightBlue))
            "broken clouds" -> ColorGradient(getColor(R.color.blue),getColor(R.color.lightBlue))
            "overcast clouds" -> ColorGradient(getColor(R.color.black),getColor(R.color.lightBlack))
            "light shower snow" -> ColorGradient(getColor(R.color.black),getColor(R.color.lightBlack))
            "snow" -> ColorGradient(getColor(R.color.black),getColor(R.color.lightBlack))
            else -> ColorGradient(getColor(R.color.black),getColor(R.color.lightBlack))
        }
    }

    inner class SliderAdapterVH(val viewHolderItem: View) :
        SliderViewAdapter.ViewHolder(viewHolderItem) {

        var imageViewWeatherStatus: ImageView
        var ivDay1Weather: ImageView
        var ivDay2Weather: ImageView
        var ivDay3Weather: ImageView
        var textViewWeatherStatus: TextView
        var textViewLocation: TextView
        var textViewTemp: TextView
        var textViewHighTemp: TextView
        var textViewLowTemp: TextView
        var tvDay1: TextView
        var tvDay2: TextView
        var tvDay3: TextView

        init {
            imageViewWeatherStatus =
                viewHolderItem.findViewById(R.id.imageView_weather_status)
            ivDay1Weather =
                viewHolderItem.findViewById(R.id.iv_day1_weather)
            ivDay2Weather =
                viewHolderItem.findViewById(R.id.iv_day2_weather)
            ivDay3Weather =
                viewHolderItem.findViewById(R.id.iv_day3_weather)

            textViewWeatherStatus =
                viewHolderItem.findViewById(R.id.textView_weather_status)
            textViewLocation =
                viewHolderItem.findViewById(R.id.textView_location)
            textViewTemp =
                viewHolderItem.findViewById(R.id.textView_temp)
            textViewHighTemp =
                viewHolderItem.findViewById(R.id.textView_high_temp)
            textViewLowTemp =
                viewHolderItem.findViewById(R.id.textView_low_temp)

            tvDay1 =
                viewHolderItem.findViewById(R.id.tv_day1)
            tvDay2 =
                viewHolderItem.findViewById(R.id.tv_day2)
            tvDay3 =
                viewHolderItem.findViewById(R.id.tv_day3)
        }
    }
}