package com.github.dmchoull.rxmvvmsample.epics

import com.github.dmchoull.rxmvvmsample.actions.POST_EVENT
import com.github.dmchoull.rxmvvmsample.eventbus.EventBus
import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.yheriatovych.reductor.observable.rxjava2.Epic
import com.yheriatovych.reductor.observable.rxjava2.Epics

fun eventBus(eventBus: EventBus): Epic<AppState> {
    return Epic { actions, _ ->
        actions.filter(Epics.ofType(POST_EVENT))
                .doOnNext { action -> eventBus.post(action.getValue<Any>(0)) }
                .ignoreElements()
                .toObservable()
    }
}
