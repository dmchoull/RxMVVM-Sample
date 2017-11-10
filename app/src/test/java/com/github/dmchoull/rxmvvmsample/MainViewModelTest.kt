package com.github.dmchoull.rxmvvmsample

import com.github.dmchoull.rxmvvmsample.actions.ApiActions
import com.github.dmchoull.rxmvvmsample.api.Weather
import com.github.dmchoull.rxmvvmsample.eventbus.EventBus
import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.yheriatovych.reductor.Actions
import com.yheriatovych.reductor.Dispatcher
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class MainViewModelTest {
    private val apiActions = Actions.from(ApiActions::class.java)
    private val dispatcher = mock<Dispatcher>()
    private val eventBus = EventBus()
    private val viewModel = MainViewModel(dispatcher, Observable.empty(), eventBus)

    @Test
    @DisplayName("listens for errors on event bus and republishes")
    fun publishesEventBusErrors() {
        val testObserver = TestObserver<Throwable>()
        viewModel.throwable.subscribe(testObserver)

        viewModel.init()

        val t = Throwable()
        eventBus.post(t)

        testObserver.assertValue(t)
    }

    @Test
    @DisplayName("publishes state changes to city")
    fun publishesCity() {
        val viewModel = MainViewModel(dispatcher, Observable.just(AppState("Toronto", null)), eventBus)

        val testObserver = TestObserver<String>()
        viewModel.city.subscribe(testObserver)

        viewModel.init()

        testObserver.assertValue("Toronto")
    }

    @Test
    @DisplayName("publishes state changes to current weather conditions")
    fun publishesCurrentConditions() {
        val weather = Weather(-5.0, 1034.0, 41.0, -5.0, -3.0)
        val viewModel = MainViewModel(dispatcher, Observable.just(AppState(null, weather)), eventBus)

        val testObserver = TestObserver<Weather>()
        viewModel.currentConditions.subscribe(testObserver)

        viewModel.init()

        testObserver.assertValue(weather)
    }

    @Test
    @DisplayName("clears disposables when onCleared is called")
    fun onCleared() {
        viewModel.init()
        viewModel.onCleared()
        assertThat(viewModel.disposables.size()).isZero()
    }

    @Test
    @DisplayName("dispatches an action to query current weather by city name")
    fun search() {
        viewModel.search("Toronto")
        verify(dispatcher).dispatch(apiActions.lookupCurrentWeather("Toronto"))
    }
}
