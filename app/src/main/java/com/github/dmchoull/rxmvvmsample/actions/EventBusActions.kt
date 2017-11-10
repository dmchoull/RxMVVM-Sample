package com.github.dmchoull.rxmvvmsample.actions

import com.yheriatovych.reductor.Action
import com.yheriatovych.reductor.annotations.ActionCreator

const val POST_EVENT = "POST_EVENT"

@ActionCreator
interface EventBusActions {
    @ActionCreator.Action(value = POST_EVENT)
    fun postEvent(value: Throwable): Action
}
