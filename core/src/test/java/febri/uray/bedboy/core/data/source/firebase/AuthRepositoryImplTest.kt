package febri.uray.bedboy.core.data.source.firebase

import febri.uray.bedboy.core.domain.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class AuthRepositoryImplTest {

    private lateinit var authRepository: AuthRepositoryImpl
    private lateinit var firebaseDataSource: FirebaseDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        firebaseDataSource = mock(FirebaseDataSource::class.java)
        authRepository = AuthRepositoryImpl(firebaseDataSource)
    }

    @Test
    fun `signInWithGoogle returns correct User`(): Unit = runBlocking {
        // Given
        val idToken = "test_id_token"
        val expectedUser = User("123", "Test User", "test@example.com", "url/to/photo")
        `when`(firebaseDataSource.signInWithGoogle(idToken)).thenReturn(expectedUser)

        // When
        val actualUser = authRepository.signInWithGoogle(idToken)

        // Then
        assertEquals(expectedUser, actualUser)
        verify(firebaseDataSource).signInWithGoogle(idToken)
    }

    @Test
    fun `isUserLoggedIn returns true when user is logged in`() {
        // Given
        `when`(firebaseDataSource.isUserLoggedIn()).thenReturn(true)

        // When
        val isLoggedIn = authRepository.isUserLoggedIn()

        // Then
        assertEquals(true, isLoggedIn)
        verify(firebaseDataSource).isUserLoggedIn()
    }

    @Test
    fun `getCurrentUser returns correct User`() {
        // Given
        val expectedUser = User("123", "Test User", "test@example.com", "url/to/photo")
        `when`(firebaseDataSource.getCurrentUser()).thenReturn(expectedUser)

        // When
        val actualUser = authRepository.getCurrentUser()

        // Then
        assertEquals(expectedUser, actualUser)
        verify(firebaseDataSource).getCurrentUser()
    }

    @Test
    fun `signOut calls signOut on firebaseDataSource`() = runBlocking {
        // When
        authRepository.signOut()

        // Then
        verify(firebaseDataSource).signOut()
    }
}
