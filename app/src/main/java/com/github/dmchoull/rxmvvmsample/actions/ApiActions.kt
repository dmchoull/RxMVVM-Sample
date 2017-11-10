package com.github.dmchoull.rxmvvmsample.actions

import com.github.dmchoull.rxmvvmsample.api.WeatherResponse
import com.yheriatovych.reductor.Action
import com.yheriatovych.reductor.annotations.ActionCreator

const val LOOKUP_CURRENT_WEATHER = "LOOKUP_CURRENT_WEATHER"
const val CURRENT_WEATHER_RESPONSE = "CURRENT_WEATHER_RESPONSE"

@ActionCreator
interface ApiActions {
    @ActionCreator.Action(value = LOOKUP_CURRENT_WEATHER)
    fun lookupCurrentWeather(city: String): Action

    @ActionCreator.Action(value = CURRENT_WEATHER_RESPONSE)
    fun currentWeatherResponse(response: WeatherResponse): Action
}
