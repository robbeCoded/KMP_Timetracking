package de.cgi.android.auth

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.cgi.common.data.model.responses.AuthResult
import de.cgi.common.repository.AuthRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.M)
class AuthViewModel(
    private val useCase: AuthUseCase
) : ViewModel(){


}

