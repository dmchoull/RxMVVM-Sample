package com.github.dmchoull.rxmvvmsample.eventbus

import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class EventBus {
    val errors: Subject<Throwable> = PublishSubject.create()

    fun post(value: Any) {
        when (value) {
            is Throwable -> errors.onNext(value)
            else -> throw IllegalArgumentException("Unsupported event with type: ${value::class}")
        }
    }
}
