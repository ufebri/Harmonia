package febri.uray.bedboy.core.domain.repository

import febri.uray.bedboy.core.domain.model.User


interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): User?
    fun isUserLoggedIn(): Boolean
    fun getCurrentUser(): User?
    suspend fun signOut()
}
