package febri.uray.bedboy.core.domain.repository

import androidx.paging.PagingData
import febri.uray.bedboy.core.domain.model.News
import kotlinx.coroutines.flow.Flow


interface IAppRepository {

    suspend fun insertNews(news: News)
    fun getAllNews(keyword: String): Flow<PagingData<News>>
    fun getBooleanPreferenceKey(key: String): Flow<Boolean>
    fun saveBooleanPreferenceKey(key: String, newState: Boolean)
}