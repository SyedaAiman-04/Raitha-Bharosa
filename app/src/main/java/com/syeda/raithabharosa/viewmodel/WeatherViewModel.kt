package com.syeda.raithabharosa.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syeda.raithabharosa.data.repository.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _advice = MutableStateFlow("")
    val advice = _advice

    private val _feelsLike = MutableStateFlow("")
    val feelsLike: StateFlow<String> = _feelsLike

    private val _humidity = MutableStateFlow("")
    val humidity: StateFlow<String> = _humidity

    private val _temperature = MutableStateFlow("Loading...")
    val temperature: StateFlow<String> = _temperature

    private val _weatherType = MutableStateFlow("")
    val weatherType: StateFlow<String> = _weatherType

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setError(message: String) {
        _temperature.value = message
        _feelsLike.value = ""
        _humidity.value = ""
    }

    fun getWeather(city: String) {
        viewModelScope.launch {

            _isLoading.value = true
            try {
                val response = RetrofitInstance.api.getWeather(
                    city = "$city,IN",
                    apiKey = "d950a2c9608aeb8a4891bf22cfe49c04"
                )

                _weatherType.value = response.weather[0].main
                _temperature.value = String.format("%.1f °C", response.main.temp)
                _feelsLike.value = "${response.main.feels_like.toInt()} °C"
                _humidity.value = "${response.main.humidity} %"

                val temp = response.main.temp
                val humidityVal = response.main.humidity
                val condition = response.weather[0].main

                _advice.value = when {
                    condition == "Rain" -> "🌧️ Rain expected. Avoid spraying pesticides."
                    temp > 35 -> "🔥 High temperature. Increase irrigation."
                    humidityVal > 80 -> "💧 High humidity. Risk of fungal diseases."
                    condition == "Clear" -> "☀️ Good weather for farming activities."
                    else -> "🌱 Conditions are normal. Monitor crops regularly."
                }
            } catch (e: Exception) {
                _temperature.value = "City not found"
                _weatherType.value = ""
            }
            _isLoading.value = false
        }
    }
}