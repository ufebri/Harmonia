package febri.uray.bedboy.core.data.source.remote.network

import febri.uray.bedboy.core.BuildConfig
import febri.uray.bedboy.core.data.source.remote.response.Responses
import febri.uray.bedboy.core.util.DateUtil
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun getEverythingNews(
        @Query("q") query: String,
        @Query("from") from: String = DateUtil.getYesterdayTimestamp(),
        @Query("to") to: String = DateUtil.getTodayTimestamp(),
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int = 1,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): Responses
}