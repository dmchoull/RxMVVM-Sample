package com.github.dmchoull.rxmvvmsample.reducers

import com.github.dmchoull.rxmvvmsample.actions.ApiActions
import com.github.dmchoull.rxmvvmsample.models.WeatherConditions
import com.github.dmchoull.rxmvvmsample.util.buildWeatherResponse
import com.yheriatovych.reductor.Actions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class WeatherReducerTest {
    private val reducer = WeatherReducer.create()
    private val apiActions = Actions.from(ApiActions::class.java)

    @Test
    @DisplayName("sets the initial state")
    fun initialState() {
        val initialState = reducer.initialState()

        assertThat(initialState).isEqualTo(AppState(null, null))
    }

    @Test
    @DisplayName("updates the state from an API response")
    fun currentWeatherResponse() {
        val response = buildWeatherResponse()
        val currentState = AppState(null, null)
        val expectedState = AppState(response.name, WeatherConditions.build(response))

        val action = apiActions.currentWeatherResponse(response)
        val state = reducer.reduce(currentState, action)

        assertThat(state).isEqualTo(expectedState)
    }
}
