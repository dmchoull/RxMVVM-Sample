package com.github.dmchoull.rxmvvmsample.epics

import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.yheriatovych.reductor.observable.rxjava2.Epic
import timber.log.Timber

fun logActions(): Epic<AppState> {
    return Epic { actions, _ ->
        actions.doOnNext { action -> Timber.d("Action: %s", action) }
                .ignoreElements()
                .toObservable()
    }
}
