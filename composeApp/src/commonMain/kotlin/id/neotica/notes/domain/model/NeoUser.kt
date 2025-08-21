package id.neotica.notes.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class NeoUser(
    val id: String? = null,
    val username: String,
    val email: String,
    val password: String,
    val createdAt: Long? = null
)