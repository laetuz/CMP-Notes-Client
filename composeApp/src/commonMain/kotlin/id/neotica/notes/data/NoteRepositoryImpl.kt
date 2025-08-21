package id.neotica.notes.data

import co.touchlab.kermit.Logger
import id.neotica.notes.domain.NoteRepository
import id.neotica.notes.domain.model.ApiResult
import id.neotica.notes.domain.model.Note
import id.neotica.notes.utils.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val client: HttpClient,
    private val sessionManager: SessionManager
): NoteRepository {

    override fun getNotes(): Flow<ApiResult<List<Note>>> = flow {
        emit(ApiResult.Loading())
        try {
            val token = "${sessionManager.getToken.first().token}"
            val respond = client.get("$baseUrl/user/notes"){
                headers.append(HttpHeaders.Authorization, "Bearer $token")
            }.body<List<Note>>()
            Logger.d("✨token:") {token}
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    override fun getPublicNotes(): Flow<ApiResult<List<Note>>> = flow {
        emit(ApiResult.Loading())
        try {
            val respond = client.get("$baseUrl/notes").body<List<Note>>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    override fun getNote(id: String): Flow<ApiResult<Note>> = flow {
        Logger.d("getNote $baseUrl/notes/$id")
        emit(ApiResult.Loading())

        val token = "${sessionManager.getToken.first().token}"

        try {
            val respond = client.get("$baseUrl/user/notes/$id"){
                headers.append(HttpHeaders.Authorization, "Bearer $token")
            }.body<Note>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            Logger.e("Error: ${e.message}")
            emit(ApiResult.Error(e.message))
        }
    }

    override fun getPublicNote(id: String): Flow<ApiResult<Note>> = flow {
        Logger.d("getNote $baseUrl/notes/$id")
        emit(ApiResult.Loading())

        try {
            val respond = client.get("$baseUrl/notes/$id").body<Note>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            Logger.e("Error: ${e.message}")
            emit(ApiResult.Error(e.message))
        }
    }

    override fun postNote(note: Note): Flow<ApiResult<Note>> = flow {
        emit(ApiResult.Loading())
        val newNote = note
        val token = "${sessionManager.getToken.first().token}"

        try {
            val respond = client.post("$baseUrl/notes") {
                headers {
                    append(HttpHeaders.Authorization, "Bearer $token")
                    contentType(ContentType.Application.Json)
                }
                setBody(newNote)
            }.body<Note>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    override fun postPublicNote(note: Note): Flow<ApiResult<Note>> = flow {
        emit(ApiResult.Loading())
        val newNote = note

        try {
            val respond = client.post("$baseUrl/notes") {
                headers {
                    contentType(ContentType.Application.Json)
                }
                setBody(newNote)
            }.body<Note>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    override fun updateNote(noteId: String, note: Note): Flow<ApiResult<Note>> = flow {
        val updatedNote = Note(
            title = note.title,
            content = note.content
        )

        try {
            val respond = client.put("$baseUrl/notes/$noteId") {
                headers {
                    contentType(ContentType.Application.Json)
                }
                setBody(updatedNote)
            }.body<Note>()
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    override fun deleteNote(noteId: String): Flow<ApiResult<String>> = flow {
        try {
            val deleteNote = client.delete("$baseUrl/notes/$noteId")
            emit(ApiResult.Success(deleteNote.toString()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

}