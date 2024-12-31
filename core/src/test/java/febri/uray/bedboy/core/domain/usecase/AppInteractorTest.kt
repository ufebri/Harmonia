package febri.uray.bedboy.core.domain.usecase

import androidx.paging.PagingData
import febri.uray.bedboy.core.domain.model.News
import febri.uray.bedboy.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class AppInteractorTest {

    private lateinit var appInteractor: AppInteractor
    private val appRepository: IAppRepository = mock(IAppRepository::class.java)
    private val news = News(title = "Test News", url = "", urlToImage = "", publishedAt = "", source = "")

    @Before
    fun setUp() {
        appInteractor = AppInteractor(appRepository)
    }

    @Test
    fun `insertNews calls repository insertNews`() = runTest {
        // Given


        // When
        appInteractor.insertNews(news)

        // Then
        verify(appRepository).insertNews(news)
    }

    @Test
    fun `getAllNews calls repository getAllNews and returns PagingData`() = runTest {
        // Given
        val keyword = "Test"
        val pagingData = PagingData.from(listOf(news))
        `when`(appRepository.getAllNews(keyword)).thenReturn(flowOf(pagingData))

        // When
        val result = appInteractor.getAllNews(keyword).first()

        // Then
        verify(appRepository).getAllNews(keyword)
        assert(result == pagingData)
    }

    @Test
    fun `getBooleanPreferenceKey calls repository getBooleanPreferenceKey and returns Flow`() = runTest {
        // Given
        val key = "dark_mode"
        val preferenceFlow: Flow<Boolean> = flowOf(true)
        `when`(appRepository.getBooleanPreferenceKey(key)).thenReturn(preferenceFlow)

        // When
        val result = appInteractor.getBooleanPreferenceKey(key).first()

        // Then
        verify(appRepository).getBooleanPreferenceKey(key)
        assert(result)
    }

    @Test
    fun `saveBooleanPreferenceKey calls repository saveBooleanPreferenceKey`() = runTest {
        // Given
        val key = "dark_mode"
        val newState = true

        // When
        appInteractor.saveBooleanPreferenceKey(key, newState)

        // Then
        verify(appRepository).saveBooleanPreferenceKey(key, newState)
    }
}