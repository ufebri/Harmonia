package febri.uray.bedboy.core.data.source.remote

import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import febri.uray.bedboy.core.data.source.remote.network.ApiService
import febri.uray.bedboy.core.data.source.remote.response.Responses
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = mock(ApiService::class.java)
        remoteDataSource = RemoteDataSource(apiService)
    }

    @Test
    fun `getDetailUser should return success when API call succeeds`() = runTest {
        // Given
        val query = "sample query"
        val mockResponse = Responses(status = "ok", totalResults = 1)
        `when`(apiService.getEverythingNews(query)).thenReturn(mockResponse)

        // When
        val result = remoteDataSource.getDetailUser(query).first()

        // Then
        assert(result is ApiResponse.Success)
        assertEquals(mockResponse, (result as ApiResponse.Success).data)
    }
}
