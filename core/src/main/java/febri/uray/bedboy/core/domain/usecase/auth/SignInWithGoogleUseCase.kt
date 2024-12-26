package febri.uray.bedboy.core.domain.usecase.auth

import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.AuthRepository

class SignInWithGoogleUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(idToken: String): User? {
        return authRepository.signInWithGoogle(idToken)
    }
}