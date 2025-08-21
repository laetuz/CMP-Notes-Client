package id.neotica.notes.data

import id.neotica.notes.domain.model.ApiResult
import id.neotica.notes.domain.model.TokenData
import id.neotica.notes.utils.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val client: HttpClient
) {
    fun login(username: String, password: String): Flow<ApiResult<TokenData>> = flow {
        emit(ApiResult.Loading())
        try {
            val respond = client.get("$baseUrl/auth") {
                headers.append("username", username)
                headers.append("password", password)
            }.body<TokenData>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) { emit(ApiResult.Error(e.message)) }
    }

}