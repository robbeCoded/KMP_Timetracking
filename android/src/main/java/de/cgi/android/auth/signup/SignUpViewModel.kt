package de.cgi.android.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.android.auth.SignUpState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class SignUpViewModel (
    private val useCase: SignUpUseCase
) : ViewModel(){
    
    private val _signUpState = MutableStateFlow<SignUpState?>(SignUpState.Loading)
    val signUpState: StateFlow<SignUpState?> = _signUpState

    private val _signUpEmail = MutableStateFlow("")
    private val signUpEmail: StateFlow<String> = _signUpEmail

    private val _signUpPassword = MutableStateFlow("")
    private val signUpPassword: StateFlow<String> = _signUpPassword

    private val _signUpName = MutableStateFlow("")
    private val signUpName: StateFlow<String> = _signUpName

    private var signUpJob: Job? = null
    fun signUp() {
        signUpJob?.cancel()
        signUpJob = useCase.signUp(
            name = signUpName.value,
            email = signUpEmail.value,
            password = signUpPassword.value
        ).onEach {
            _signUpState.value = it
        }.launchIn(viewModelScope)
    }

    fun signUpEmailChanged(email: String) {
        _signUpEmail.value = email
    }

    fun signUpPasswordChanged(password: String) {
        _signUpPassword.value = password
    }

    fun signUpNameChanged(name: String) {
        _signUpName.value = name
    }

    fun isEmailValid(): Boolean {
        return _signUpEmail.value.contains("@")
    }

    fun isPasswordValid(): Boolean {
        return _signUpPassword.value.length >= 8
    }
}