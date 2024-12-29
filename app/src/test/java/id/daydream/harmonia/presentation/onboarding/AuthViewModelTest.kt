package id.daydream.harmonia.presentation.onboarding

import febri.uray.bedboy.core.domain.model.User
import febri.uray.bedboy.core.domain.usecase.auth.SignInWithGoogleUseCase
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var signInWithGoogleUseCase: SignInWithGoogleUseCase
    private lateinit var viewModel: AuthViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        signInWithGoogleUseCase = mock(SignInWithGoogleUseCase::class.java)
        viewModel = AuthViewModel(signInWithGoogleUseCase)
    }

    @After
    fun tearDown() {
        // 3) Reset main dispatcher
        Dispatchers.resetMain()
    }

    @Test
    fun `userState is updated when signInGoogle is successful`() = runTest {
        // Given (setup)
        val fakeUser = User("123", "Test User", "test@example.com", urlPhoto = "")
        whenever(signInWithGoogleUseCase("valid_token")).thenReturn(fakeUser)

        // Siapkan list untuk menampung hasil userState
        val results = mutableListOf<User?>()

        // Mulai collect userState di background
        val job = launch {
            viewModel.userState.toList(results)
        }

        // When (action)
        viewModel.signInGoogle("valid_token")

        // Tunggu sampai semua coroutine selesai
        advanceUntilIdle()

        // Berhenti collect agar tes tidak berjalan terus
        job.cancel()

        // Then (assertion)
        // Index 0 -> nilai awal (null)
        // Index 1 -> user setelah signInGoogle
        assertEquals(2, results.size)
        assertNull(results[0])
        assertEquals(fakeUser, results[1])
    }

    @Test
    fun `userState remains null when signInGoogle returns null`() = runTest {
        // Given
        whenever(signInWithGoogleUseCase("invalid_token")).thenReturn(null)

        val results = mutableListOf<User?>()
        val job = launch {
            viewModel.userState.toList(results)
        }

        // When
        viewModel.signInGoogle("invalid_token")

        advanceUntilIdle()
        job.cancel()

        // Then
        // Nilai pertama null (initial), nilai kedua juga null (karena signInWithGoogleUseCase balikin null)
        assertEquals(1, results.size)
        assertNull(results[0])
    }
}