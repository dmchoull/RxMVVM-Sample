package com.github.dmchoull.rxmvvmsample.di

import com.github.dmchoull.rxmvvmsample.BuildConfig
import com.github.dmchoull.rxmvvmsample.epics.eventBus
import com.github.dmchoull.rxmvvmsample.epics.logActions
import com.github.dmchoull.rxmvvmsample.epics.lookupCurrentWeather
import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.github.dmchoull.rxmvvmsample.reducers.WeatherReducer
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.yheriatovych.reductor.Dispatcher
import com.yheriatovych.reductor.Store
import com.yheriatovych.reductor.observable.rxjava2.EpicMiddleware
import com.yheriatovych.reductor.observable.rxjava2.Epics
import com.yheriatovych.reductor.rxjava2.RxStore
import io.reactivex.Observable

val storeModule = Kodein.Module {
    bind<Store<AppState>>() with singleton {
        val epics = mutableListOf(
                eventBus(instance()),
                lookupCurrentWeather(instance())
        )

        if (BuildConfig.DEBUG) {
            epics.add(logActions())
        }

        val rootEpic = Epics.combineEpics<AppState>(epics)

        Store.create(WeatherReducer.create(), EpicMiddleware.create(rootEpic))
    }

    bind<Dispatcher>() with singleton { instance<Store<AppState>>() as Dispatcher }

    bind<Observable<AppState>>() with singleton { RxStore.asObservable(instance<Store<AppState>>()) }
}
