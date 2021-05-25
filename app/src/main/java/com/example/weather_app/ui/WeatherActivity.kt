package com.example.weather_app.ui

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.model.WeatherResponse
import com.example.weather_app.ui.adapter.WeatherSliderAdapter
import com.example.weather_app.util.ResponseHandler
import com.example.weather_app.util.showToast
import com.example.weatherapp.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.android.synthetic.main.activity_weather.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class WeatherActivity : AppCompatActivity() {

    private val viewModel by viewModel<WeatherViewModel>()
    lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    val weatherInfoList = mutableListOf<WeatherResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        Timber.d("onCreate")

        initializeTheView()
        viewModelWorks()
        clickListener()
    }

    private fun initializeTheView() {
        //set Default city
        viewModel.getWeatherByCity("California")
    }

    private fun clickListener() {
        btn_add_city.setOnClickListener {
            showFindByCityNameDialog()
        }
    }

    private fun viewModelWorks() {
        viewModel.getWeatherByCityResponse.observe(this, { response ->
            when (response) {
                is ResponseHandler.Success -> {
                    response.data?.let { weatherResponse ->
                        Timber.d("weatherResponse ${weatherResponse}")

                        weatherInfoList.add(weatherResponse)
                        weatherInfoList.reverse()

                        val adapter = WeatherSliderAdapter(this, weatherInfoList)
                        sliderView.setSliderAdapter(adapter)

                        progress_circular.visibility = View.GONE
                    }
                }
                is ResponseHandler.Loading -> {
                    Timber.d("Loading ")
                    progress_circular.visibility = View.VISIBLE
                }
                is ResponseHandler.Failure -> {
                    Timber.d("response ${response}")
                    progress_circular.visibility = View.GONE
                    if(response.errorMessage == "404")
                    {
                        showToast("City not found!")
                    }
                    else {
                        showToast("Failure ${response.errorMessage}")
                    }
                }
            }
        })
    }

    private fun showFindByCityNameDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_weather)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)

        val cityName = dialog.findViewById(R.id.et_city_name) as TextInputEditText
        val btnFind = dialog.findViewById(R.id.btn_find) as MaterialButton

        btnFind.setOnClickListener {
            viewModel.getWeatherByCity(cityName.text.toString())
            dialog.dismiss()
        }

        this.resources?.let {
            val displayMetrics = it.displayMetrics
            val dialogWidth = (displayMetrics.widthPixels * 0.75).toInt()
            val dialogHeight = WindowManager.LayoutParams.WRAP_CONTENT
            dialog.window?.setLayout(dialogWidth, dialogHeight)
            dialog.show()
        }
    }

}