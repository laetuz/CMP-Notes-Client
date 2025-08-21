package id.neotica.notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Note(
    val userId: String? = null,
    val id: String? = null,
    val title: String?,
    val content: String?,
    val createdAt: Long? = null,
    val updatedAt: Long? = null
)
