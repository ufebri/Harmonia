package febri.uray.bedboy.core.domain.model

import android.os.Parcelable
import febri.uray.bedboy.core.data.source.local.entity.NewsEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    var title: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var source: String? = null
) : Parcelable

fun News.toEntities(): NewsEntity {
    return NewsEntity(
        title = title,
        publishedDate = publishedAt,
        coverImage = urlToImage,
        url = url,
        sourceName = source
    )
}