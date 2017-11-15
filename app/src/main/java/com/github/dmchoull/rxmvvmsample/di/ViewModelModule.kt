package com.github.dmchoull.rxmvvmsample.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import com.github.dmchoull.rxmvvmsample.MainActivity
import com.github.dmchoull.rxmvvmsample.MainViewModel
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.androidActivityScope

val viewModelModule = Kodein.Module {
    bind<ViewModelFactory>() with singleton { ViewModelFactory(kodein) }

    bind<ViewModel>("MainViewModel") with provider { MainViewModel(instance(), instance(), instance()) }

    bind<MainViewModel>() with scopedSingleton(androidActivityScope) {
        getViewModel(instance<MainActivity>(), instance(), MainViewModel::class.java)
    }
}

private fun <T : ViewModel> getViewModel(activity: FragmentActivity, factory: ViewModelFactory, modelClass: Class<T>) =
        ViewModelProviders.of(activity, factory).get(modelClass)

private class ViewModelFactory(private val kodein: Kodein) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider: () -> ViewModel = kodein.provider(modelClass.simpleName)
        return provider() as T
    }
}
