package id.neotica.notes.domain

sealed class ApiResult<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Loading<T>(data: T? = null) : ApiResult<T>(data)
    class Error<T>(errorMessage: String? = null) : ApiResult<T>(null, errorMessage)
}