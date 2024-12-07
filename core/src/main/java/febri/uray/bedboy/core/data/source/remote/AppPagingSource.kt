package febri.uray.bedboy.core.data.source.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import febri.uray.bedboy.core.data.source.remote.network.ApiService
import febri.uray.bedboy.core.data.source.remote.response.toListDomain
import febri.uray.bedboy.core.domain.model.News

class AppPagingSource(private val apiService: ApiService, private val keyword: String) :
    PagingSource<Int, News>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val responseData =
                apiService.getEverythingNews(page = params.key ?: 1, query = keyword)

            LoadResult.Page(
                data = responseData.toListDomain(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (responseData.toListDomain().isEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}