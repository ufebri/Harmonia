package febri.uray.bedboy.core.domain.usecase.auth

import febri.uray.bedboy.core.domain.repository.AuthRepository

class CheckUserLoggedInUseCase(
    private val authRepository: AuthRepository
) {
    operator fun invoke(): Boolean {
        return authRepository.isUserLoggedIn()
    }
}