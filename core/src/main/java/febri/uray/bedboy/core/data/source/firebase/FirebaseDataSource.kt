package febri.uray.bedboy.core.data.source.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.tasks.await

class FirebaseDataSource(
    private val firebaseAuth: FirebaseAuth
) {

    suspend fun signInWithGoogle(idToken: String): User? {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = firebaseAuth.signInWithCredential(credential).await()
        val firebaseUser = authResult.user
        return firebaseUser?.let {
            User(
                uid = it.uid,
                displayName = it.displayName,
                email = it.email,
                urlPhoto = it.photoUrl.toString()
            )
        }
    }

    fun isUserLoggedIn(): Boolean = (firebaseAuth.currentUser != null)

    fun getCurrentUser(): User? {
        val currentUser = firebaseAuth.currentUser
        return currentUser?.let {
            User(
                uid = it.uid,
                displayName = it.displayName,
                email = it.email,
                urlPhoto = it.photoUrl.toString()
            )
        }
    }

    fun signOut() = firebaseAuth.signOut()

}
