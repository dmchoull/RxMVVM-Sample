@file:Suppress("UNUSED_PARAMETER")

package com.github.dmchoull.rxmvvmsample.reducers

import com.github.dmchoull.rxmvvmsample.actions.ApiActions
import com.github.dmchoull.rxmvvmsample.actions.CURRENT_WEATHER_RESPONSE
import com.github.dmchoull.rxmvvmsample.api.WeatherResponse
import com.github.dmchoull.rxmvvmsample.models.WeatherConditions
import com.yheriatovych.reductor.Reducer
import com.yheriatovych.reductor.annotations.AutoReducer

@AutoReducer
abstract class WeatherReducer : Reducer<AppState> {
    @AutoReducer.InitialState
    fun initialState() = AppState(null, null)

    @AutoReducer.Action(value = CURRENT_WEATHER_RESPONSE, from = ApiActions::class)
    fun currentWeatherResponse(state: AppState, response: WeatherResponse): AppState =
            state.copy(city = response.name, currentConditions = WeatherConditions.build(response))

    companion object {
        fun create(): WeatherReducer = WeatherReducerImpl()
    }
}
