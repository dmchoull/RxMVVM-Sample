# RxMVVM-Sample
Sample Android Kotlin application using an MVVM + Redux style architecture with RxJava2.

## Architecture highlights
 * View (i.e. Activity/Fragment) observes state from its View Model and reacts to any changes
 * View handles UI events and calls methods on its View Model to make things happen
 * View Model dispatches actions to trigger state changes or cause side effects (ex: API call)
 * Side effects of actions are implemented in [redux-observable](https://redux-observable.js.org/) style epics
 * Application state is kept in a [Redux](https://github.com/reactjs/redux) style global store
 * View Model observes state changes from the store and publishes the relevant parts to its observer (the View)

## Libraries used
 * [Android Architecture Components View Model](https://developer.android.com/topic/libraries/architecture/viewmodel.html) - Lifecycle aware view model
 * [RxJava](https://github.com/ReactiveX/RxJava) - Asynchronous programming with observable streams
 * [RxBindings](https://github.com/JakeWharton/RxBinding) - RxJava bindings for Android's UI widgets
 * [Reductor](https://github.com/Yarikx/reductor) - Redux style store + epics
 * [Retrofit](http://square.github.io/retrofit/) - HTTP client
 * [Kodein](https://salomonbrys.github.io/Kodein/) - Kotlin Dependency Injection
 * [Robolectric](http://robolectric.org/)
 * [JUnit 5](http://junit.org/junit5/)
 * [Mockito](https://github.com/mockito/mockito)
 * [Mockito-Kotlin](https://github.com/mockito/mockito)
