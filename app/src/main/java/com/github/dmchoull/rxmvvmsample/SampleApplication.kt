package com.github.dmchoull.rxmvvmsample

import android.app.Application
import com.github.dmchoull.rxmvvmsample.di.apiModule
import com.github.dmchoull.rxmvvmsample.di.storeModule
import com.github.dmchoull.rxmvvmsample.di.viewModelModule
import com.github.dmchoull.rxmvvmsample.eventbus.EventBus
import com.github.salomonbrys.kodein.*
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

class SampleApplication : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(apiModule)
        import(storeModule)
        import(viewModelModule)

        bind<EventBus>() with singleton { EventBus() }
    }

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
