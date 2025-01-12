package febri.uray.bedboy.core.domain.model

import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import febri.uray.bedboy.core.data.source.remote.response.Articles
import febri.uray.bedboy.core.data.source.remote.response.Responses
import febri.uray.bedboy.core.data.source.remote.response.Source
import febri.uray.bedboy.core.data.source.remote.response.toEntities
import febri.uray.bedboy.core.data.source.remote.response.toListDomain
import org.junit.Assert.assertEquals
import org.junit.Test

class ResponsesTest {

    @Test
    fun `toListDomain converts Responses to List of News`() {
        // Arrange
        val responses = Responses(
            status = "ok",
            totalResults = 2,
            articles = arrayListOf(
                Articles(
                    source = Source(id = "1", name = "Source 1"),
                    author = "Author 1",
                    title = "Title 1",
                    description = "Description 1",
                    url = "https://example.com/news1",
                    urlToImage = "https://example.com/image1.jpg",
                    publishedAt = "2024-12-28",
                    content = "Content 1"
                ),
                Articles(
                    source = Source(id = "2", name = "Source 2"),
                    author = "Author 2",
                    title = "Title 2",
                    description = "Description 2",
                    url = "https://example.com/news2",
                    urlToImage = "https://example.com/image2.jpg",
                    publishedAt = "2024-12-29",
                    content = "Content 2"
                )
            )
        )

        // Act
        val result = responses.toListDomain()

        // Assert
        assertEquals(2, result.size)

        val firstNews = result[0]
        assertEquals("Title 1", firstNews.title)
        assertEquals("2024-12-28", firstNews.publishedAt)
        assertEquals("https://example.com/image1.jpg", firstNews.urlToImage)
        assertEquals("https://example.com/news1", firstNews.url)
        assertEquals("Source 1", firstNews.source)

        val secondNews = result[1]
        assertEquals("Title 2", secondNews.title)
        assertEquals("2024-12-29", secondNews.publishedAt)
        assertEquals("https://example.com/image2.jpg", secondNews.urlToImage)
        assertEquals("https://example.com/news2", secondNews.url)
        assertEquals("Source 2", secondNews.source)
    }

    @Test
    fun `toEntities should map Articles to NewsEntity correctly`() {
        // Arrange
        val articles = Articles(
            title = "Sample Title",
            publishedAt = "2025-01-01T12:00:00Z",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            source = Source(
                id = "source-id",
                name = "Source Name"
            )
        )

        val expectedEntity = NewsEntity(
            title = "Sample Title",
            publishedDate = "2025-01-01T12:00:00Z",
            url = "https://example.com",
            coverImage = "https://example.com/image.jpg",
            sourceName = "Source Name"
        )

        // Act
        val result = articles.toEntities()

        // Assert
        assertEquals(expectedEntity, result)
    }

    @Test
    fun `toEntities should handle null source gracefully`() {
        // Arrange
        val articles = Articles(
            title = "Sample Title",
            publishedAt = "2025-01-01T12:00:00Z",
            url = "https://example.com",
            urlToImage = "https://example.com/image.jpg",
            source = null
        )

        val expectedEntity = NewsEntity(
            title = "Sample Title",
            publishedDate = "2025-01-01T12:00:00Z",
            url = "https://example.com",
            coverImage = "https://example.com/image.jpg",
            sourceName = null
        )

        // Act
        val result = articles.toEntities()

        // Assert
        assertEquals(expectedEntity, result)
    }
}
