package febri.uray.bedboy.core.data.source.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class FirebaseDataSourceTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDataSource: FirebaseDataSource

    @Before
    fun setUp() {
        firebaseAuth = mock(FirebaseAuth::class.java)
        firebaseDataSource = FirebaseDataSource(firebaseAuth)
    }

    @Test
    fun `isUserLoggedIn returns true when user is logged in`() {
        // Mock currentUser
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(firebaseAuth.currentUser).thenReturn(mockFirebaseUser)

        // Test
        assertTrue(firebaseDataSource.isUserLoggedIn())
    }

    @Test
    fun `isUserLoggedIn returns false when user is not logged in`() {
        `when`(firebaseAuth.currentUser).thenReturn(null)

        // Test
        assertFalse(firebaseDataSource.isUserLoggedIn())
    }

    @Test
    fun `getCurrentUser returns user when user is logged in`() {
        // Mock FirebaseUser
        val mockFirebaseUser = mock(FirebaseUser::class.java)
        `when`(mockFirebaseUser.uid).thenReturn("123")
        `when`(mockFirebaseUser.displayName).thenReturn("Test User")
        `when`(mockFirebaseUser.email).thenReturn("test@example.com")
        `when`(mockFirebaseUser.photoUrl).thenReturn(null)
        `when`(firebaseAuth.currentUser).thenReturn(mockFirebaseUser)

        // Test
        val user = firebaseDataSource.getCurrentUser()
        assertNotNull(user)
        assertEquals("123", user?.uid)
        assertEquals("Test User", user?.displayName)
        assertEquals("test@example.com", user?.email)
    }

    @Test
    fun `getCurrentUser returns null when no user is logged in`() {
        `when`(firebaseAuth.currentUser).thenReturn(null)

        // Test
        val user = firebaseDataSource.getCurrentUser()
        assertNull(user)
    }

    @Test
    fun `signOut calls firebaseAuth signOut`() {
        // Test
        firebaseDataSource.signOut()

        // Verify
        verify(firebaseAuth).signOut()
    }
}
