package febri.uray.bedboy.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import febri.uray.bedboy.core.data.source.remote.response.Articles

@Entity(tableName = "news_entity")
data class NewsEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    var id: Int = 0,

    @ColumnInfo("title")
    var title: String? = null,

    @ColumnInfo("published_date")
    var publishedDate: String? = null,

    @ColumnInfo("cover")
    var coverImage: String? = null,

    @ColumnInfo("url_news")
    var url: String? = null,

    @ColumnInfo("source")
    var sourceName: String? = null
)

fun NewsEntity.toDomain(): Articles {
    return Articles(
        title = title,
        publishedAt = publishedDate,
        url = url,
        urlToImage = coverImage
    )
}