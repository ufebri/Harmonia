package febri.uray.bedboy.core.data.source

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.preferencesOf
import febri.uray.bedboy.core.data.source.local.LocalDataSource
import febri.uray.bedboy.core.data.source.remote.RemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class AppRepositoryTest {

    private lateinit var appRepository: AppRepository
    private lateinit var remoteDataSource: RemoteDataSource
    private lateinit var localDataSource: LocalDataSource
    private lateinit var dataStore: DataStore<Preferences>

    @Before
    fun setUp() {
        remoteDataSource = mock(RemoteDataSource::class.java)
        localDataSource = mock(LocalDataSource::class.java)
        dataStore = mock(DataStore::class.java) as DataStore<Preferences>
        appRepository = AppRepository(remoteDataSource, localDataSource, dataStore)
    }

    @Test
    fun `getBooleanPreferenceKey should return correct boolean value`() = runTest {
        val key = "test_key"
        val mKey = booleanPreferencesKey(key)
        val preferences = preferencesOf(mKey to true)

        `when`(dataStore.data).thenReturn(flowOf(preferences))

        val result = appRepository.getBooleanPreferenceKey(key).first()

        assertTrue(result)
    }
}
