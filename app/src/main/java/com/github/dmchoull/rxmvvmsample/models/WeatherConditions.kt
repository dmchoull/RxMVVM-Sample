package com.github.dmchoull.rxmvvmsample.models

import com.github.dmchoull.rxmvvmsample.api.WeatherResponse
import java.util.*

data class WeatherConditions(val temp: Double, val pressure: Double, val humidity: Double, val windSpeed: Double,
                             val sunrise: Date, val sunset: Date) {
    companion object {
        fun build(weatherResponse: WeatherResponse): WeatherConditions =
                WeatherConditions(weatherResponse.main.temp,
                        weatherResponse.main.pressure,
                        weatherResponse.main.humidity,
                        weatherResponse.wind.speed,
                        Date(weatherResponse.sys.sunrise * 1000),
                        Date(weatherResponse.sys.sunset * 1000))
    }
}
