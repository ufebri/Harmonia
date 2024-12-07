package febri.uray.bedboy.core.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: NewsEntity)

    @Query("SELECT * FROM news_entity ORDER BY published_date DESC")
    fun getAllNews(): Flow<List<NewsEntity>>
}