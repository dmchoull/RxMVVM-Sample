package com.github.dmchoull.rxmvvmsample.epics

import com.github.dmchoull.rxmvvmsample.actions.ApiActions
import com.github.dmchoull.rxmvvmsample.actions.EventBusActions
import com.github.dmchoull.rxmvvmsample.actions.LOOKUP_CURRENT_WEATHER
import com.github.dmchoull.rxmvvmsample.api.WeatherLookupAPI
import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.yheriatovych.reductor.Actions
import com.yheriatovych.reductor.observable.rxjava2.Epic
import com.yheriatovych.reductor.observable.rxjava2.Epics
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun lookupCurrentWeather(weatherLookupAPI: WeatherLookupAPI): Epic<AppState> {
    val apiActions = Actions.from(ApiActions::class.java)
    val eventBusActions = Actions.from(EventBusActions::class.java)

    return Epic { actions, _ ->
        actions.filter(Epics.ofType(LOOKUP_CURRENT_WEATHER))
                .flatMap { action ->
                    weatherLookupAPI.lookupCurrentConditions(action.getValue(0))
                            .subscribeOn(Schedulers.io())
                            .map { response -> apiActions.currentWeatherResponse(response) }
                            .onErrorResumeNext({ t: Throwable -> Observable.just(eventBusActions.postEvent(t)) })
                }
    }
}
