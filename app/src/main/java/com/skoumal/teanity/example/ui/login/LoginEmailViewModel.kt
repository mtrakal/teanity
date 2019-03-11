package com.skoumal.teanity.example.ui.login

import com.evernote.android.state.State as SavedState
import com.skoumal.teanity.example.R
import com.skoumal.teanity.example.data.repository.RegistrationRepository
import com.skoumal.teanity.example.util.isEmail
import com.skoumal.teanity.example.util.isPassword
import com.skoumal.teanity.extensions.applySchedulers
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.viewevents.SnackbarEvent
import com.skoumal.teanity.viewmodel.LoadingViewModel

class LoginEmailViewModel(
    private val registrationRepo: RegistrationRepository
) : LoadingViewModel() {

    @SavedState
    var email = KObservableField("")
    @SavedState
    var emailError = KObservableField("")
    @SavedState
    var password = KObservableField("")
    @SavedState
    var passwordError = KObservableField("")

    init {
        state = State.LOADED
    }

    fun loginButtonClicked() {
        registrationRepo
            .login {
                email = this@LoginEmailViewModel.email.value
                password = this@LoginEmailViewModel.password.value

                onEvaluate { email.isEmail(emailError) && password.isPassword(passwordError) }
            }
            .applyViewModel(this)
            .applySchedulers()
            .subscribeK(onComplete = this::loginSucceeded, onError = this::loginFailed)
            .add()
    }

    private fun loginSucceeded() {
        LoginEmailFragment.EVENT_NAVIGATE_TO_MAIN_ACTIVITY.publish()
    }

    private fun loginFailed(throwable: Throwable) {
        SnackbarEvent(R.string.login_failed).publish()
    }
}
