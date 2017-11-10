package com.github.dmchoull.rxmvvmsample.reducers

import com.github.dmchoull.rxmvvmsample.api.Weather

data class AppState(val city: String?, val currentConditions: Weather?)
