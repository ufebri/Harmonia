package id.daydream.harmonia.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.usecase.auth.SignInWithGoogleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCase: SignInWithGoogleUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<User?>(null)
    val userState = _userState.asStateFlow()

    // Supaya menandai error atau loading, Anda bisa pakai sealed class State
    // Sekarang sederhana saja
    private val _errorState = MutableStateFlow<String?>(null)
    val errorState = _errorState.asStateFlow()

    fun signInGoogle(idToken: String) {
        viewModelScope.launch {
            try {
                val user = authUseCase(idToken)
                if (user != null) {
                    _userState.value = user
                } else {
                    _errorState.value = "Failed to sign in"
                }
            } catch (e: Exception) {
                _errorState.value = e.message
            }
        }
    }
}