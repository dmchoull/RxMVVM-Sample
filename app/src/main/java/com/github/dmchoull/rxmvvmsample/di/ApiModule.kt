package com.github.dmchoull.rxmvvmsample.di

import com.github.dmchoull.rxmvvmsample.api.WeatherLookupAPI
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

const val WEATHER_API_BASE_URL = "http://api.openweathermap.org"

val apiModule = Kodein.Module {
    bind<Retrofit>() with singleton {
        Retrofit.Builder()
                .baseUrl(WEATHER_API_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpClient())
                .build()
    }

    bind<WeatherLookupAPI>() with singleton {
        val retrofit: Retrofit = instance()
        retrofit.create(WeatherLookupAPI::class.java)
    }
}
