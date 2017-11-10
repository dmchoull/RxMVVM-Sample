package com.github.dmchoull.rxmvvmsample.di

import com.github.dmchoull.rxmvvmsample.MainViewModel
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.androidActivityScope
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.scopedSingleton
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

val testViewModelModule = Kodein.Module {
    bind<MainViewModel>() with scopedSingleton(androidActivityScope) {
        mock<MainViewModel> {
            on { throwable } doReturn PublishSubject.create()
            on { city } doReturn BehaviorSubject.create()
            on { currentConditions } doReturn BehaviorSubject.create()
        }
    }
}
