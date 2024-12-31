package febri.uray.bedboy.core.domain.model

data class User(
    val uid: String,
    val displayName: String?,
    val email: String?,
    val urlPhoto: String?
)