package febri.uray.bedboy.core.domain.usecase.user

import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.AuthRepository

class UserUseCase(private val authRepository: AuthRepository) {

    // Function to get current user
    fun getCurrentUser(): User? = authRepository.getCurrentUser()

    // Function to sign out
    suspend fun signOut() = authRepository.signOut()

}