package febri.uray.bedboy.core.data.source.remote

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import febri.uray.bedboy.core.data.source.remote.network.ApiService
import febri.uray.bedboy.core.data.source.remote.response.Articles
import febri.uray.bedboy.core.data.source.remote.response.Responses
import febri.uray.bedboy.core.data.source.remote.response.Source
import febri.uray.bedboy.core.data.source.remote.response.toListDomain
import febri.uray.bedboy.core.domain.model.News
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class AppPagingSourceTest {

    private lateinit var fakeApiService: FakeApiService
    private lateinit var pagingSource: AppPagingSource

    @Before
    fun setup() {
        fakeApiService = FakeApiService()
        pagingSource = AppPagingSource(fakeApiService, "test keyword")
    }

    @Test
    fun `load returns page when successful`() = runTest {
        // Setup fake response data
        fakeApiService.fakeResponse = Responses(
            articles = arrayListOf(
                Articles(
                    title = "Test News",
                    publishedAt = "2025-01-01",
                    urlToImage = "https://example.com/image.jpg",
                    url = "https://example.com",
                    source = Source(name = "Test Source")
                )
            )
        )

        // Call load
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Verify the result
        val expected = PagingSource.LoadResult.Page(
            data = fakeApiService.fakeResponse.toListDomain(),
            prevKey = null,
            nextKey = 2
        )
        assertEquals(expected, result)
    }

    @Test
    fun `load returns error when exception occurs`() = runTest {
        // Setup fake exception
        fakeApiService.shouldThrowError = true

        // Call load
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // Verify the result is an error
        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `getRefreshKey returns correct key`() {
        // Create fake PagingState
        val pagingState = PagingState(
            pages = listOf(
                PagingSource.LoadResult.Page(
                    data = listOf(
                        News(
                            title = "Test News",
                            publishedAt = "2025-01-01",
                            urlToImage = "https://example.com/image.jpg",
                            url = "https://example.com",
                            source = "Test Source"
                        )
                    ),
                    prevKey = null,
                    nextKey = 2
                )
            ),
            anchorPosition = 0,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )

        // Verify the refresh key
        val refreshKey = pagingSource.getRefreshKey(pagingState)
        assertEquals(1, refreshKey)
    }

    // Fake ApiService implementation for testing
    class FakeApiService : ApiService {
        var fakeResponse: Responses = Responses()
        var shouldThrowError: Boolean = false

        override suspend fun getEverythingNews(
            query: String,
            from: String,
            to: String,
            sortBy: String,
            page: Int,
            apiKey: String
        ): Responses {
            if (shouldThrowError) throw Exception("Fake Network Error")
            return fakeResponse
        }
    }
}
