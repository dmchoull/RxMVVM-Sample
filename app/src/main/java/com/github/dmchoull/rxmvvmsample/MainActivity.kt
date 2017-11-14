package com.github.dmchoull.rxmvvmsample

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.github.dmchoull.rxmvvmsample.models.WeatherConditions
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.android.KodeinAppCompatActivity
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.with
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : KodeinAppCompatActivity() {
    internal val viewModel: MainViewModel by with(this as Activity).instance()

    private val disposables = CompositeDisposable()
    private val dateFormat = SimpleDateFormat("h:mm a z", Locale.US)

    override fun provideOverridingModule() = Kodein.Module {
        bind<MainActivity>() with instance(this@MainActivity)
    }

    @SuppressLint("MissingSuperCall") // FIXME: why does it think super call is missing?
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        disposables.addAll(
                RxView.clicks(searchButton)
                        .subscribe({ _ ->
                            viewModel.search(cityQuery.text.toString())
                            cityQuery.setText("")
                        }),

                viewModel.city
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ city ->
                            currentConditionsTitle.visibility = View.VISIBLE
                            currentConditionsTitle.text = getString(R.string.current_conditions, city)
                        }),

                viewModel.currentConditions
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ conditions -> updateConditions(conditions) }),

                viewModel.throwable
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext({ t -> Timber.e(t) })
                        .subscribe(this::showError)
        )

        viewModel.init()
    }

    private fun updateConditions(conditions: WeatherConditions) {
        currentTempLabel.visibility = View.VISIBLE
        currentTemperature.visibility = View.VISIBLE
        currentTemperature.text = getString(R.string.temperature_c, conditions.temp)

        pressureLabel.visibility = View.VISIBLE
        pressure.visibility = View.VISIBLE
        pressure.text = conditions.pressure.toString()

        humidityLabel.visibility = View.VISIBLE
        humidity.visibility = View.VISIBLE
        humidity.text = conditions.humidity.toString()

        sunriseLabel.visibility = View.VISIBLE
        sunrise.visibility = View.VISIBLE
        sunrise.text = dateFormat.format(conditions.sunrise)

        sunsetLabel.visibility = View.VISIBLE
        sunset.visibility = View.VISIBLE
        sunset.text = dateFormat.format(conditions.sunset)
    }

    private fun showError(throwable: Throwable) {
        val message = when (throwable) {
            is HttpException -> if (throwable.code() == 404) R.string.city_not_found else R.string.bad_request
            is IOException -> R.string.connection_failed
            else -> R.string.something_went_wrong
        }

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        disposables.clear()
    }
}
