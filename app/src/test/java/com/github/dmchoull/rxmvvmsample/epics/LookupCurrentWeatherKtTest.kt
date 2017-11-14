package com.github.dmchoull.rxmvvmsample.epics

import com.github.dmchoull.rxmvvmsample.actions.ApiActions
import com.github.dmchoull.rxmvvmsample.actions.EventBusActions
import com.github.dmchoull.rxmvvmsample.api.WeatherLookupAPI
import com.github.dmchoull.rxmvvmsample.api.WeatherResponse
import com.github.dmchoull.rxmvvmsample.extensions.RxImmediateScheduler
import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.github.dmchoull.rxmvvmsample.util.buildWeatherResponse
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.yheriatovych.reductor.Actions
import com.yheriatovych.reductor.Store
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(RxImmediateScheduler::class)
internal class LookupCurrentWeatherKtTest {
    private val store = mock<Store<AppState>>()
    private val apiActions = Actions.from(ApiActions::class.java)

    @Nested
    @DisplayName("when API call completes successfully")
    inner class OnSuccess {
        private val response: WeatherResponse = buildWeatherResponse()
        private val weatherLookupAPI = mock<WeatherLookupAPI> {
            on { lookupCurrentConditions("Toronto") } doReturn Observable.just(response)
        }

        @Test
        @DisplayName("returns response action")
        fun apiResponse() {
            val actions = Observable.just(apiActions.lookupCurrentWeather("Toronto"))

            val epic = lookupCurrentWeather(weatherLookupAPI).run(actions, store)

            val testObserver = TestObserver<Any>()
            epic.subscribe(testObserver)

            testObserver.assertValue(apiActions.currentWeatherResponse(response))
        }
    }

    @Nested
    @DisplayName("when API call fails")
    inner class OnFailure {
        private val eventBusActions = Actions.from(EventBusActions::class.java)
        private val throwable = Throwable("API error")
        private val weatherLookupAPI = mock<WeatherLookupAPI> {
            on { lookupCurrentConditions("Toronto") } doReturn Observable.error(throwable)
        }

        @Test
        @DisplayName("returns action to post error to event bus")
        fun apiFailure() {
            val actions = Observable.just(apiActions.lookupCurrentWeather("Toronto"))

            val epic = lookupCurrentWeather(weatherLookupAPI).run(actions, store)

            val testObserver = TestObserver<Any>()
            epic.subscribe(testObserver)

            testObserver.assertValue(eventBusActions.postEvent(throwable))
        }
    }
}
