package febri.uray.bedboy.core.domain.usecase.user

import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.repository.AuthRepository
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class UserUseCaseTest {

    private val userRepository = mock(AuthRepository::class.java) // Mock UserRepository
    private val userUseCase = UserUseCase(userRepository) // Instance UserUseCase

    @Test
    fun `getCurrentUser returns user from repository`() {
        // Given: Mock repository returns a user
        val mockUser =
            User(uid = "123", email = "test@example.com", displayName = "Test User", urlPhoto = "")
        `when`(userRepository.getCurrentUser()).thenReturn(mockUser)

        // When: UseCase calls getCurrentUser
        val currentUser = userUseCase.getCurrentUser()

        // Then: Verify repository is called and correct user is returned
        verify(userRepository).getCurrentUser()
        assertEquals(mockUser, currentUser)
    }

    @Test
    fun `getCurrentUser returns null when repository returns null`() {
        // Given: Mock repository returns null
        `when`(userRepository.getCurrentUser()).thenReturn(null)

        // When: UseCase calls getCurrentUser
        val currentUser = userUseCase.getCurrentUser()

        // Then: Verify repository is called and null is returned
        verify(userRepository).getCurrentUser()
        assertNull(currentUser)
    }

    @Test
    fun `signOut calls repository signOut`() = runBlocking {
        // When: UseCase calls signOut
        userUseCase.signOut()

        // Then: Verify repository's signOut is called
        verify(userRepository).signOut()
    }
}