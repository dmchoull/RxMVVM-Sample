# RxMVVM-Sample
Sample Android Kotlin application using an MVVM + Redux style architecture with RxJava2.

## Architecture highlights
* View (i.e. Activity/Fragment) observes state from its View Model and reacts to any changes
* View handles UI events and calls methods on its View Model to make things happen
* View Model dispatches actions to trigger state changes or cause side effects (ex: API call)
* Side effects of actions are implemented in redux-observable style epics
* Application state is kept in a Redux-style global store
* View Model observes state changes from the store and publishes the relevant parts to its observer (the View)
