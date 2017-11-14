package com.github.dmchoull.rxmvvmsample.models

import com.github.dmchoull.rxmvvmsample.util.buildWeatherResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

internal class WeatherConditionsTest {
    private val weatherResponse = buildWeatherResponse()

    @Test
    @DisplayName("can be built from weather response")
    fun create() {
        val weather = WeatherConditions.build(weatherResponse)

        assertThat(weather.temp).isEqualTo(weatherResponse.main.temp)
        assertThat(weather.pressure).isEqualTo(weatherResponse.main.pressure)
        assertThat(weather.humidity).isEqualTo(weatherResponse.main.humidity)
        assertThat(weather.windSpeed).isEqualTo(weatherResponse.wind.speed)
        assertThat(weather.sunrise).isEqualTo(Date(weatherResponse.sys.sunrise))
        assertThat(weather.sunset).isEqualTo(Date(weatherResponse.sys.sunset))
    }
}
