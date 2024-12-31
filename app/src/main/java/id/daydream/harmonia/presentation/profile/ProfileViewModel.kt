package id.daydream.harmonia.presentation.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userUseCase: UserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User?>()
    val user: LiveData<User?> get() = _user

    fun fetchCurrentUser() {
        _user.value = userUseCase.getCurrentUser()
    }

    fun logout() {
        viewModelScope.launch {
            userUseCase.signOut()
        }
    }
}