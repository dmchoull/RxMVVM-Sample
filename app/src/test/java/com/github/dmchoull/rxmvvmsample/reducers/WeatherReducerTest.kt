package com.github.dmchoull.rxmvvmsample.reducers

import com.github.dmchoull.rxmvvmsample.actions.ApiActions
import com.github.dmchoull.rxmvvmsample.api.*
import com.yheriatovych.reductor.Actions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class WeatherReducerTest {
    private val reducer = WeatherReducer.create()
    private val apiActions = Actions.from(ApiActions::class.java)

    @Test
    @DisplayName("Sets the initial state")
    fun initialState() {
        val action = apiActions.lookupCurrentWeather("Toronto")
        val initialState = reducer.reduce(null, action)

        assertThat(initialState.city).isNull()
        assertThat(initialState.currentConditions).isNull()
    }

    @Test
    @DisplayName("Updates the state from an API response")
    fun currentWeatherResponse() {
        val currentState = AppState(null, null)
        val response = buildWeatherResponse()

        val action = apiActions.currentWeatherResponse(response)

        val (city, currentConditions) = reducer.reduce(currentState, action)

        assertThat(city).isEqualTo(response.name)
        assertThat(currentConditions).isEqualTo(response.main)
    }

    private fun buildWeatherResponse(): WeatherResponse {
        return WeatherResponse(
                Coord(-79.42, 43.7), emptyList(),
                Weather(-5.0, 1034.0, 41.0, -5.0, -3.0),
                14484,
                Wind(5.7, 320, 7.5),
                1234L,
                Sys("ca", 1510315612L, 1510350963L),
                6167865,
                "Toronto"
        )
    }
}
