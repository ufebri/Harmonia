package febri.uray.bedboy.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import febri.uray.bedboy.core.data.source.local.LocalDataSource
import febri.uray.bedboy.core.data.source.remote.AppPagingSource
import febri.uray.bedboy.core.data.source.remote.RemoteDataSource
import febri.uray.bedboy.core.domain.model.News
import febri.uray.bedboy.core.domain.model.toEntities
import febri.uray.bedboy.core.domain.repository.IAppRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val dataStore: DataStore<Preferences>
) : IAppRepository {

    override suspend fun insertNews(news: News) = localDataSource.insertNews(news.toEntities())

    override fun getAllNews(keyword: String): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(30),
            pagingSourceFactory = {
                AppPagingSource(remoteDataSource.client, keyword)
            }
        ).flow
    }

    override fun getBooleanPreferenceKey(key: String): Flow<Boolean> =
        dataStore.data.map { preferences ->
            val mKey = booleanPreferencesKey(key)
            preferences[mKey] ?: false
        }

    override fun saveBooleanPreferenceKey(key: String, newState: Boolean) {
        runBlocking {
            launch {
                dataStore.edit { preferences ->
                    val mKey = booleanPreferencesKey(key)
                    preferences[mKey] = newState
                }
            }
        }
    }
}