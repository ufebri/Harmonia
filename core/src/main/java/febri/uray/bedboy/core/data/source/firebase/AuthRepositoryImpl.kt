package febri.uray.bedboy.core.data.source.firebase

import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val firebaseDataSource: FirebaseDataSource
) : AuthRepository {

    override suspend fun signInWithGoogle(idToken: String): User? =
        firebaseDataSource.signInWithGoogle(idToken)

    override fun isUserLoggedIn(): Boolean = firebaseDataSource.isUserLoggedIn()

    override fun getCurrentUser(): User? = firebaseDataSource.getCurrentUser()

    override suspend fun signOut() = firebaseDataSource.signOut()

}
