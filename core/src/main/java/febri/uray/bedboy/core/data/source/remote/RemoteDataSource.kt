package febri.uray.bedboy.core.data.source.remote

import android.util.Log
import febri.uray.bedboy.core.data.source.remote.network.ApiResponse
import febri.uray.bedboy.core.data.source.remote.network.ApiService
import febri.uray.bedboy.core.data.source.remote.response.Responses
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    private val tag = "RemoteDataSource"
    val client = apiService

    suspend fun getDetailUser(query: String): Flow<ApiResponse<Responses>> {
        return flow {
            val response = apiService.getEverythingNews(query = query)
            try {
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error("error"))
                Log.e(tag, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}

