package febri.uray.bedboy.core.domain.auth

import febri.uray.bedboy.core.domain.repository.AuthRepository
import febri.uray.bedboy.core.domain.usecase.auth.CheckUserLoggedInUseCase
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class CheckUserLoggedInUseCaseTest {

    private lateinit var authRepository: AuthRepository
    private lateinit var checkUserLoggedInUseCase: CheckUserLoggedInUseCase

    @Before
    fun setUp() {
        authRepository = mock(AuthRepository::class.java)
        checkUserLoggedInUseCase = CheckUserLoggedInUseCase(authRepository)
    }

    @Test
    fun `returns true when user is logged in`() {
        // Given
        whenever(authRepository.isUserLoggedIn()).thenReturn(true)

        // When
        val result = checkUserLoggedInUseCase()

        // Then
        assertTrue(result)
    }

    @Test
    fun `returns false when user is not logged in`() {
        // Given
        whenever(authRepository.isUserLoggedIn()).thenReturn(false)

        // When
        val result = checkUserLoggedInUseCase()

        // Then
        assertFalse(result)
    }
}