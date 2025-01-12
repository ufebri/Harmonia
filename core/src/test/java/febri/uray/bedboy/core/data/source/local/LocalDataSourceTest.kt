package febri.uray.bedboy.core.data.source.local

import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import febri.uray.bedboy.core.data.source.local.room.dao.NewsDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class LocalDataSourceTest {

    @Mock
    private lateinit var mockNewsDao: NewsDao

    private lateinit var localDataSource: LocalDataSource

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        localDataSource = LocalDataSource(mockNewsDao)
    }

    @Test
    fun `insertNews should call insertNews on NewsDao`() = runBlocking {
        // Given
        val newsEntity = NewsEntity(
            title = "Test Title",
            publishedDate = "2023-12-01",
            coverImage = "https://example.com/image.jpg",
            url = "https://example.com",
            sourceName = "Test Source"
        )

        // When
        localDataSource.insertNews(newsEntity)

        // Then
        Mockito.verify(mockNewsDao).insertNews(newsEntity)
    }

    @Test
    fun `getAllNews should return all news from NewsDao`() = runBlocking {
        // Given
        val newsEntities = listOf(
            NewsEntity(
                title = "Test Title 1",
                publishedDate = "2023-12-01",
                coverImage = "https://example.com/image1.jpg",
                url = "https://example1.com",
                sourceName = "Test Source 1"
            ),
            NewsEntity(
                title = "Test Title 2",
                publishedDate = "2023-12-02",
                coverImage = "https://example.com/image2.jpg",
                url = "https://example2.com",
                sourceName = "Test Source 2"
            )
        )
        Mockito.`when`(mockNewsDao.getAllNews()).thenReturn(flowOf(newsEntities))

        // When
        val result = localDataSource.getAllNews()

        // Then
        result.collect { collectedNews ->
            assertEquals(newsEntities, collectedNews)
        }
    }
}
