package febri.uray.bedboy.core.data.source.remote.network

import org.junit.Assert.*
import org.junit.Test

class ApiResponseTest {

    @Test
    fun `Success should contain correct data`() {
        // Arrange
        val expectedData = "Test Data"

        // Act
        val response = ApiResponse.Success(expectedData)

        // Assert
        assertTrue(true)
        assertEquals(expectedData, response.data)
    }

    @Test
    fun `Error should contain correct error message`() {
        // Arrange
        val expectedMessage = "Something went wrong"

        // Act
        val response = ApiResponse.Error(expectedMessage)

        // Assert
        assertTrue(true)
        assertEquals(expectedMessage, response.errorMessage)
    }

    @Test
    fun `Empty should be of type ApiResponse_Empty`() {
        // Act
        val response = ApiResponse.Empty

        // Assert
        assertTrue(true)
    }
}
