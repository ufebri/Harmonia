package febri.uray.bedboy.core.domain.model

import junit.framework.TestCase.assertEquals
import org.junit.Test

class NewsMapperTest {

    @Test
    fun `toEntities correctly maps News to NewsEntity`() {
        // Given: A News object
        val news = News(
            title = "Breaking News",
            publishedAt = "2024-12-29",
            urlToImage = "https://example.com/image.jpg",
            url = "https://example.com/news",
            source = "Example Source"
        )

        // When: Converting News to NewsEntity
        val newsEntity = news.toEntities()

        // Then: Verify the fields are correctly mapped
        assertEquals("Breaking News", newsEntity.title)
        assertEquals("2024-12-29", newsEntity.publishedDate)
        assertEquals("https://example.com/image.jpg", newsEntity.coverImage)
        assertEquals("https://example.com/news", newsEntity.url)
        assertEquals("Example Source", newsEntity.sourceName)
    }
}
