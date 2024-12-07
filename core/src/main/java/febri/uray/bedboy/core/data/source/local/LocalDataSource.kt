package febri.uray.bedboy.core.data.source.local

import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import febri.uray.bedboy.core.data.source.local.room.dao.NewsDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val newsDao: NewsDao) {

    suspend fun insertNews(mData: NewsEntity) = newsDao.insertNews(mData)

    fun getAllNews() = newsDao.getAllNews()
}