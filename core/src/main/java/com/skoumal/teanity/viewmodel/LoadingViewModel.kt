package com.skoumal.teanity.viewmodel

import androidx.databinding.Bindable
import com.skoumal.teanity.BR
import kotlinx.coroutines.async

abstract class LoadingViewModel(defaultState: State = State.LOADING) :
    StatefulViewModel<LoadingViewModel.State>(defaultState) {

    val loading @Bindable get() = state == State.LOADING
    val loaded @Bindable get() = state == State.LOADED
    val loadingFailed @Bindable get() = state == State.LOADING_FAILED

    override suspend fun induceRefresh() = loading { super.induceRefresh() }

    override fun notifyStateChanged() {
        notifyPropertyChanged(BR.loading)
        notifyPropertyChanged(BR.loaded)
        notifyPropertyChanged(BR.loadingFailed)
    }

    inline fun loading(crossinline body: suspend () -> Unit) {
        async {
            state = State.LOADING
            body()
        }.invokeOnCompletion {
            state = if (it == null) State.LOADED else State.LOADING_FAILED
        }
    }

    enum class State {
        LOADED, LOADING, LOADING_FAILED
    }

    //region Rx
    protected fun <T> Observable<T>.applyViewModel(viewModel: LoadingViewModel, allowFinishing: Boolean = true) =
        doOnSubscribe { viewModel.state = State.LOADING }
            .doOnError { viewModel.state = State.LOADING_FAILED }
            .doOnNext { if (allowFinishing) viewModel.state = State.LOADED }

    protected fun <T> Single<T>.applyViewModel(viewModel: LoadingViewModel, allowFinishing: Boolean = true) =
        doOnSubscribe { viewModel.state = State.LOADING }
            .doOnError { viewModel.state = State.LOADING_FAILED }
            .doOnSuccess { if (allowFinishing) viewModel.state = State.LOADED }

    protected fun <T> Maybe<T>.applyViewModel(viewModel: LoadingViewModel, allowFinishing: Boolean = true) =
        doOnSubscribe { viewModel.state = State.LOADING }
            .doOnError { viewModel.state = State.LOADING_FAILED }
            .doOnComplete { if (allowFinishing) viewModel.state = State.LOADED }
            .doOnSuccess { if (allowFinishing) viewModel.state = State.LOADED }

    protected fun <T> Flowable<T>.applyViewModel(viewModel: LoadingViewModel, allowFinishing: Boolean = true) =
        doOnSubscribe { viewModel.state = State.LOADING }
            .doOnError { viewModel.state = State.LOADING_FAILED }
            .doOnNext { if (allowFinishing) viewModel.state = State.LOADED }

    protected fun Completable.applyViewModel(viewModel: LoadingViewModel, allowFinishing: Boolean = true) =
        doOnSubscribe { viewModel.state = State.LOADING }
            .doOnError { viewModel.state = State.LOADING_FAILED }
            .doOnComplete { if (allowFinishing) viewModel.state = State.LOADED }
    //endregion
}