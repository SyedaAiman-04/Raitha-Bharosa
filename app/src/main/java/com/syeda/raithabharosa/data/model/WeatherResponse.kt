package com.syeda.raithabharosa.data.model
data class WeatherResponse(
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,
    val feels_like: Double,
    val humidity: Int
)

data class Weather(
    val main: String
)