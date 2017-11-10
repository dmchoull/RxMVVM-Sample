package com.github.dmchoull.rxmvvmsample.eventbus

import io.reactivex.observers.TestObserver
import io.reactivex.subjects.PublishSubject
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class EventBusTest {
    private val eventBus = EventBus()

    @Test
    @DisplayName("has an errors PublishSubject")
    fun errors() {
        assertThat(eventBus.errors).isInstanceOf(PublishSubject::class.java)
    }

    @Test
    @DisplayName("posts a Throwable to the errors subject")
    fun postThrowable() {
        val testObserver = TestObserver<Throwable>()
        eventBus.errors.subscribe(testObserver)

        val t = Throwable()
        eventBus.post(t)

        testObserver.assertValue(t)
    }

    @Test
    @DisplayName("throws an exception if an unsupported type is given")
    fun postUnsupported() {
        assertThrows(IllegalArgumentException::class.java, { eventBus.post(3.14) })
    }
}
