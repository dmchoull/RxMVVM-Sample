package com.github.dmchoull.rxmvvmsample.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherLookupAPI {
    // Calls OpenWeatherMap current weather API with a city name query
    // API doc: https://openweathermap.org/current
    @GET("data/2.5/weather?APPID=3966cec830484e669fdd6665c381e6cb")
    fun lookupCurrentConditions(@Query("q") city: String,
                                @Query("units") unit: String = "metric"): Observable<WeatherResponse>
}

data class WeatherResponse(val coord: Coord,
                           val weather: List<WeatherMoreInfo>,
                           val main: Weather,
                           val visibility: Int,
                           val wind: Wind,
                           val dt: Long,
                           val sys: Sys,
                           val id: Int,
                           val name: String)

data class Coord(val lat: Double, val lon: Double)

data class WeatherMoreInfo(val id: Int, val main: String, val description: String, val icon: String)

data class Weather(val temp: Double, val pressure: Double, val humidity: Double, val temp_min: Double,
                   val temp_max: Double)

data class Wind(val speed: Double, val deg: Int, val gust: Double)

data class Sys(val country: String, val sunrise: Long, val sunset: Long)
