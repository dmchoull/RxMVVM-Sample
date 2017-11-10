package com.github.dmchoull.rxmvvmsample

import android.app.Application
import com.github.dmchoull.rxmvvmsample.di.testViewModelModule
import com.github.dmchoull.rxmvvmsample.eventbus.EventBus
import com.github.salomonbrys.kodein.*
import com.nhaarman.mockito_kotlin.mock

@Suppress("unused") // Used by Robolectric
class TestSampleApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(testViewModelModule)

        bind<EventBus>() with singleton { mock<EventBus>() }
    }
}
