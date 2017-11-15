package com.github.dmchoull.rxmvvmsample

import android.widget.Button
import android.widget.TextView
import com.github.dmchoull.rxmvvmsample.models.WeatherConditions
import com.github.dmchoull.rxmvvmsample.util.buildWeatherResponse
import com.nhaarman.mockito_kotlin.verify
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import org.assertj.android.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowToast
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class)
internal class MainActivityTest {
    private val activityController = Robolectric.buildActivity(MainActivity::class.java).setup()
    private val activity: MainActivity = activityController.get()

    @Test
    @DisplayName("calls init on view model when created")
    fun initsViewModelOnCreate() {
        verify(activity.viewModel).init()
    }

    @Test
    @DisplayName("current weather conditions are initially not displayed")
    fun currentConditionsInitiallyInvisible() {
        Assertions.assertThat(activity.currentConditionsTitle).isInvisible
        Assertions.assertThat(activity.currentTempLabel).isInvisible
        Assertions.assertThat(activity.currentTemperature).isInvisible
        Assertions.assertThat(activity.pressureLabel).isInvisible
        Assertions.assertThat(activity.pressure).isInvisible
        Assertions.assertThat(activity.humidityLabel).isInvisible
        Assertions.assertThat(activity.humidity).isInvisible
        Assertions.assertThat(activity.sunriseLabel).isInvisible
        Assertions.assertThat(activity.sunrise).isInvisible
        Assertions.assertThat(activity.sunsetLabel).isInvisible
        Assertions.assertThat(activity.sunset).isInvisible
    }

    @Test
    @DisplayName("current weather conditions are displayed after being observed")
    fun currentConditionsDisplayed() {
        val weatherConditions = WeatherConditions.build(buildWeatherResponse())
        activity.viewModel.currentConditions.onNext(weatherConditions)

        Assertions.assertThat(activity.currentTempLabel).isVisible
        Assertions.assertThat(activity.currentTemperature).isVisible
        Assertions.assertThat(activity.currentTemperature).hasText("-4.0˚C")

        Assertions.assertThat(activity.pressureLabel).isVisible
        Assertions.assertThat(activity.pressure).isVisible
        Assertions.assertThat(activity.pressure).hasText("1034.0")

        Assertions.assertThat(activity.humidityLabel).isVisible
        Assertions.assertThat(activity.humidity).isVisible
        Assertions.assertThat(activity.humidity).hasText("41.0")

        Assertions.assertThat(activity.sunriseLabel).isVisible
        Assertions.assertThat(activity.sunrise).isVisible
        Assertions.assertThat(activity.sunrise).hasText("7:06 AM EST")

        Assertions.assertThat(activity.sunsetLabel).isVisible
        Assertions.assertThat(activity.sunset).isVisible
        Assertions.assertThat(activity.sunset).hasText("4:56 PM EST")
    }

    @Test
    @DisplayName("calls search on view model with current query when search button is clicked")
    fun citySearchClick() {
        activity.findViewById<TextView>(R.id.cityQuery)?.text = "Toronto"
        activity.findViewById<Button>(R.id.searchButton)?.performClick()

        verify(activity.viewModel).search("Toronto")
    }

    @Test
    @DisplayName("updates display when city changes")
    fun cityChanged() {
        val title = activity.findViewById<TextView>(R.id.currentConditionsTitle)
        Assertions.assertThat(activity.currentConditionsTitle).isInvisible

        activity.viewModel.city.onNext("Toronto")
        assertThat(title.text).contains("Toronto")

        Assertions.assertThat(activity.currentConditionsTitle).isVisible

        activity.viewModel.city.onNext("Mississauga")
        assertThat(title.text).contains("Mississauga")
    }

    @Test
    @DisplayName("displays not found message when 404 error observed")
    fun displays404Errors() {
        val response: Response<Any> = Response.error(404, ResponseBody.create(null, ""))
        val t = HttpException(response)
        activity.viewModel.throwable.onNext(t)

        val toast = ShadowToast.getTextOfLatestToast()
        assertThat(toast).isEqualTo(activity.getString(R.string.city_not_found))
    }

    @Test
    @DisplayName("displays bad request message when 400 error observed")
    fun displays400Errors() {
        val response: Response<Any> = Response.error(400, ResponseBody.create(null, ""))
        val t = HttpException(response)
        activity.viewModel.throwable.onNext(t)

        val toast = ShadowToast.getTextOfLatestToast()
        assertThat(toast).isEqualTo(activity.getString(R.string.bad_request))
    }

    @Test
    @DisplayName("displays connection error message when network error observed")
    fun displaysNetworkErrors() {
        val t = IOException()
        activity.viewModel.throwable.onNext(t)

        val toast = ShadowToast.getTextOfLatestToast()
        assertThat(toast).isEqualTo(activity.getString(R.string.connection_failed))
    }

    @Test
    @DisplayName("displays error message when throwable observed")
    fun displaysErrors() {
        val t = Throwable("Test Error")
        activity.viewModel.throwable.onNext(t)

        val toast = ShadowToast.getTextOfLatestToast()
        assertThat(toast).isEqualTo(activity.getString(R.string.something_went_wrong))
    }

    @Test
    @DisplayName("clears subscribed disposables when onStop is called")
    fun onStopClears() {
        assertThat(activity.disposables.size()).isNotZero()

        activityController.stop()

        assertThat(activity.disposables.size()).isZero()
        assertThat(activity.disposables.isDisposed).isFalse()
    }
}
