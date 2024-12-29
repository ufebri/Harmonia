package febri.uray.bedboy.core.domain.auth

import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.AuthRepository
import febri.uray.bedboy.core.domain.usecase.auth.SignInWithGoogleUseCase
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class SignInWithGoogleUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var signInWithGoogleUseCase: SignInWithGoogleUseCase

    @Before
    fun setUp() {
        authRepository = mock(AuthRepository::class.java)
        signInWithGoogleUseCase = SignInWithGoogleUseCase(authRepository)
    }

    @Test
    fun `signInWithGoogle returns user when idToken is valid`() = runTest {
        // Given
        val testUser = User(uid = "123", displayName = "John Doe", email = "john@example.com", urlPhoto = "")
        whenever(authRepository.signInWithGoogle("valid_token")).thenReturn(testUser)

        // When
        val result = signInWithGoogleUseCase("valid_token")

        // Then
        assertNotNull(result)
        assertEquals("123", result?.uid)
        assertEquals("John Doe", result?.displayName)
        assertEquals("john@example.com", result?.email)
    }

    @Test
    fun `signInWithGoogle returns null when idToken is invalid`() = runTest {
        // Given
        whenever(authRepository.signInWithGoogle("invalid_token")).thenReturn(null)

        // When
        val result = signInWithGoogleUseCase("invalid_token")

        // Then
        assertNull(result)
    }
}