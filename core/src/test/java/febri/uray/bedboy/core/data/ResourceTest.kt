package febri.uray.bedboy.core.data

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class ResourceTest {

    @Test
    fun `Success creates Resource with data`() {
        // Given
        val expectedData = "Success Data"

        // When
        val resource = Resource.Success(expectedData)

        // Then
        assertEquals(expectedData, resource.data)
        assertNull(resource.message)
    }

    @Test
    fun `Loading creates Resource with nullable data`() {
        // When (null data case)
        val resourceWithNullData = Resource.Loading<String>()

        // Then
        assertNull(resourceWithNullData.data)
        assertNull(resourceWithNullData.message)

        // When (non-null data case)
        val expectedData = "Loading Data"
        val resourceWithData = Resource.Loading(expectedData)

        // Then
        assertEquals(expectedData, resourceWithData.data)
        assertNull(resourceWithData.message)
    }

    @Test
    fun `Error creates Resource with message and nullable data`() {
        // Given
        val expectedMessage = "Error Message"

        // When (null data case)
        val resourceWithNullData = Resource.Error<String>(expectedMessage)

        // Then
        assertNull(resourceWithNullData.data)
        assertEquals(expectedMessage, resourceWithNullData.message)

        // When (non-null data case)
        val expectedData = "Error Data"
        val resourceWithData = Resource.Error(expectedMessage, expectedData)

        // Then
        assertEquals(expectedData, resourceWithData.data)
        assertEquals(expectedMessage, resourceWithData.message)
    }
}
