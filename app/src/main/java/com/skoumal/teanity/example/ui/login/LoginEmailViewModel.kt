package com.skoumal.teanity.example.ui.login

import com.evernote.android.state.State
import com.skoumal.teanity.example.R
import com.skoumal.teanity.example.data.repository.RegistrationRepository
import com.skoumal.teanity.example.ui.events.SnackbarEvent
import com.skoumal.teanity.example.util.isEmail
import com.skoumal.teanity.example.util.isPassword
import com.skoumal.teanity.extensions.applySchedulers
import com.skoumal.teanity.extensions.applyViewModel
import com.skoumal.teanity.extensions.subscribeK
import com.skoumal.teanity.util.KObservableField
import com.skoumal.teanity.viewmodel.LoadingViewModel

class LoginEmailViewModel(
    private val registrationRepo: RegistrationRepository
) : LoadingViewModel() {

    @State
    var email = KObservableField("")
    @State
    var emailError = KObservableField("")
    @State
    var password = KObservableField("")
    @State
    var passwordError = KObservableField("")

    init {
        setLoaded()
    }

    fun loginButtonClicked() {
        registrationRepo
            .login {
                email = this@LoginEmailViewModel.email.value
                password = this@LoginEmailViewModel.password.value

                onEvaluate { email.isEmail(emailError) && password.isPassword(passwordError) }
                onEvaluateFailed { SnackbarEvent(R.string.login_failed).publish() }
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
