package febri.uray.bedboy.core.data

import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NetworkBoundResourceTest {

    private lateinit var networkBoundResource: FakeNetworkBoundResource

    @Before
    fun setUp() {
        networkBoundResource = FakeNetworkBoundResource()
    }

    @Test
    fun `should fetch from remote and save to local when shouldFetch is true`() = runTest {
        networkBoundResource.shouldFetchFromRemote = true
        networkBoundResource.remoteData = "Remote Data"
        networkBoundResource.localData = ""

        val result = networkBoundResource.asFlow().toList()

        assertEquals(3, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Loading)
        assertTrue(result[2] is Resource.Success)
        assertEquals("Remote Data", (result[2] as Resource.Success).data)
    }

    @Test
    fun `should emit local data when shouldFetch is false`() = runTest {
        networkBoundResource.shouldFetchFromRemote = false
        networkBoundResource.localData = "Local Data"

        val result = networkBoundResource.asFlow().toList()

        assertEquals(2, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)
        assertEquals("Local Data", (result[1] as Resource.Success).data)
    }

    @Test
    fun `should emit error when remote fetch fails`() = runTest {
        networkBoundResource.shouldFetchFromRemote = true
        networkBoundResource.localData = ""
        networkBoundResource.throwError = true

        val result = networkBoundResource.asFlow().toList()

        assertEquals(3, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Loading)
        assertTrue(result[2] is Resource.Error)
        val errorMessage = (result[2] as Resource.Error).message
        assertEquals("Fetch error", errorMessage)
    }

    @Test
    fun `should emit local data when remote fetch returns empty`() = runTest {
        networkBoundResource.shouldFetchFromRemote = true
        networkBoundResource.localData = "Local Data"
        networkBoundResource.remoteData = null

        val result = networkBoundResource.asFlow().toList()

        assertEquals(3, result.size)
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Loading)
        assertTrue(result[2] is Resource.Success)
        assertEquals("Local Data", (result[2] as Resource.Success).data)
    }

    // Helper extension function to collect all items from a Flow into a list
    private suspend fun <T> Flow<T>.toList(): List<T> {
        val result = mutableListOf<T>()
        collect { result.add(it) }
        return result
    }
}

class FakeNetworkBoundResource : NetworkBoundResource<String, String>() {

    var shouldFetchFromRemote = false
    var localData: String = "" // Ensure this is non-null
    var remoteData: String? = null
    var throwError = false

    override fun loadFromDB(): Flow<String> = flow {
        emit(localData)
    }

    override fun shouldFetch(data: String?): Boolean = shouldFetchFromRemote

    override suspend fun createCall(): Flow<ApiResponse<String>> = flow {
        if (throwError) {
            emit(ApiResponse.Error("Fetch error"))
        } else if (remoteData == null) {
            emit(ApiResponse.Empty)
        } else {
            emit(ApiResponse.Success(remoteData!!))
        }
    }

    override suspend fun saveCallResult(data: String) {
        localData = data
    }
}

