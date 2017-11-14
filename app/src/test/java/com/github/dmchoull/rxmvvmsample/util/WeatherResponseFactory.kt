package com.github.dmchoull.rxmvvmsample.util

import com.github.dmchoull.rxmvvmsample.api.*

fun buildWeatherResponse(): WeatherResponse {
    return WeatherResponse(
            Coord(-79.42, 43.7), emptyList(),
            Weather(-5.0, 1034.0, 41.0, -5.0, -3.0),
            14484,
            Wind(5.7, 320.0, 7.5),
            1234L,
            Sys("ca", 1510315612L, 1510350963L),
            6167865,
            "Toronto"
    )
}
