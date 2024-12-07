package febri.uray.bedboy.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import febri.uray.bedboy.core.data.source.local.room.dao.NewsDao

@Database(
    entities = [NewsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}