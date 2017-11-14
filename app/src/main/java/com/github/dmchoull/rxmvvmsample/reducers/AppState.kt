package com.github.dmchoull.rxmvvmsample.reducers

import com.github.dmchoull.rxmvvmsample.models.WeatherConditions

data class AppState(val city: String?, val currentConditions: WeatherConditions?)
