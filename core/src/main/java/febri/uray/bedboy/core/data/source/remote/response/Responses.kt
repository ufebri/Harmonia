package febri.uray.bedboy.core.data.source.remote.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import febri.uray.bedboy.core.domain.model.News
import kotlinx.parcelize.Parcelize

@Parcelize
data class Responses(
    @SerializedName("status") var status: String? = null,
    @SerializedName("totalResults") var totalResults: Int? = null,
    @SerializedName("articles") var articles: ArrayList<Articles> = arrayListOf()
) : Parcelable

@Parcelize
data class Articles(
    @SerializedName("source") var source: Source? = Source(),
    @SerializedName("author") var author: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("urlToImage") var urlToImage: String? = null,
    @SerializedName("publishedAt") var publishedAt: String? = null,
    @SerializedName("content") var content: String? = null
) : Parcelable

@Parcelize
data class Source(
    @SerializedName("id") var id: String? = null,
    @SerializedName("name") var name: String? = null
) : Parcelable


fun Articles.toEntities(): NewsEntity {
    return NewsEntity(
        title = title,
        publishedDate = publishedAt,
        coverImage = urlToImage,
        url = url,
        sourceName = source?.name
    )
}

fun Responses.toListDomain(): List<News> {
    val mListData = ArrayList<News>()
    articles.map {
        val mData = News(
            title = it.title,
            publishedAt = it.publishedAt,
            urlToImage = it.urlToImage,
            url = it.url,
            source = it.source?.name
        )
        mListData.add(mData)
    }
    return mListData
}