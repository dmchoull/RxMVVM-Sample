package com.github.dmchoull.rxmvvmsample

import android.widget.TextView
import org.assertj.android.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
internal class MainActivityTest {
    private var activity: MainActivity? = null

    @Before
    fun setUp() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
    }

    @Test
    fun displaysHelloWorld() {
        val textView = activity?.findViewById<TextView>(R.id.helloText)
        assertThat(textView).hasText("Hello World!")
    }
}
