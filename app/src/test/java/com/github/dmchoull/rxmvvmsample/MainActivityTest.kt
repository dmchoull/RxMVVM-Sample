package com.github.dmchoull.rxmvvmsample

import android.widget.Button
import android.widget.TextView
import com.nhaarman.mockito_kotlin.verify
import okhttp3.ResponseBody
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
    private val activity: MainActivity = Robolectric.setupActivity(MainActivity::class.java)

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

        activity.viewModel.city.onNext("Toronto")
        assertThat(title.text).contains("Toronto")

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
}
