package com.github.dmchoull.rxmvvmsample.epics

import com.github.dmchoull.rxmvvmsample.actions.EventBusActions
import com.github.dmchoull.rxmvvmsample.eventbus.EventBus
import com.github.dmchoull.rxmvvmsample.reducers.AppState
import com.nhaarman.mockito_kotlin.mock
import com.yheriatovych.reductor.Action
import com.yheriatovych.reductor.Actions
import com.yheriatovych.reductor.Store
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class EventBusKtTest {
    private val eventBus = EventBus()
    private val store = mock<Store<AppState>>()
    private val eventBusActions = Actions.from(EventBusActions::class.java)

    @Test
    @DisplayName("can post a value to the event bus")
    fun eventBusPostsSupported() {
        val testObserver = TestObserver<Throwable>()
        eventBus.errors.subscribe(testObserver)

        val t = Throwable("test error")
        val actions: Observable<Action> = Observable.just(eventBusActions.postEvent(t))
        eventBus(eventBus).run(actions, store).subscribe()

        testObserver.assertValue(t)
    }
}
