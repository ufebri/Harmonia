package febri.uray.bedboy.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import febri.uray.bedboy.core.BuildConfig
import febri.uray.bedboy.core.data.source.local.room.AppDatabase
import febri.uray.bedboy.core.data.source.local.room.dao.NewsDao
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.DB_PASS.toCharArray())
        val factory = if (BuildConfig.DEBUG) null else SupportFactory(passphrase)

        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, BuildConfig.DB_NAME
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }

    @Provides
    fun provideNewsDao(database: AppDatabase): NewsDao = database.newsDao()
}