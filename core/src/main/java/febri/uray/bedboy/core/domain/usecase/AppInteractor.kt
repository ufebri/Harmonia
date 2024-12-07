package febri.uray.bedboy.core.domain.usecase

import androidx.paging.PagingData
import febri.uray.bedboy.core.domain.model.News
import febri.uray.bedboy.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppInteractor @Inject constructor(private var appRepository: IAppRepository) :
    AppUseCase {

    override suspend fun insertNews(news: News) = appRepository.insertNews(news)

    override fun getAllNews(keyword: String): Flow<PagingData<News>> =
        appRepository.getAllNews(keyword)

    override fun getBooleanPreferenceKey(key: String): Flow<Boolean> =
        appRepository.getBooleanPreferenceKey(key)

    override fun saveBooleanPreferenceKey(key: String, newState: Boolean) =
        appRepository.saveBooleanPreferenceKey(key, newState)

}