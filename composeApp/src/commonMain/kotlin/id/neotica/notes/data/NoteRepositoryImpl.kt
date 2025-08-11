package id.neotica.notes.data

import co.touchlab.kermit.Logger
import id.neotica.notes.domain.ApiResult
import id.neotica.notes.domain.Note
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
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val client: HttpClient
) {

    fun getNotes(): Flow<ApiResult<List<Note>>> = flow {
        val respond = client.get("$baseUrl/notes").body<List<Note>>()
        emit(ApiResult.Loading())
        try {
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    fun getNote(id: String): Flow<ApiResult<Note>> = flow {
        Logger.d("getNote $baseUrl/notes/$id")
        emit(ApiResult.Loading())
        val respond = client.get("$baseUrl/notes/$id").body<Note>()
        try {
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    fun postNote(note: Note): Flow<ApiResult<Note>> = flow {
        emit(ApiResult.Loading())
        val newNote = note

        val respond = client.post("$baseUrl/notes") {
            headers {
                contentType(ContentType.Application.Json)
            }
            setBody(newNote)
        }.body<Note>()

        try {
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    fun updateNote(noteId: String, note: Note): Flow<ApiResult<Note>> = flow {
        val updatedNote = Note(
            title = note.title,
            content = note.content
        )

        val respond = client.put("$baseUrl/notes/$noteId") {
            headers {
                contentType(ContentType.Application.Json)
            }
            setBody(updatedNote)
        }.body<Note>()

        try {
            emit(ApiResult.Success(respond))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

    fun deleteNote(noteId: String): Flow<ApiResult<String>> = flow {
        val deleteNote = client.delete("$baseUrl/notes/$noteId")
        try {
            emit(ApiResult.Success(deleteNote.toString()))
        } catch (e: Exception) {
            emit(ApiResult.Error(e.message))
        }
    }

}