package id.neotica.notes.data

import co.touchlab.kermit.Logger
import id.neotica.notes.domain.Note
import id.neotica.notes.utils.baseUrl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NoteRepositoryImpl(
    private val client: HttpClient
) {

    fun getNotes(): Flow<List<Note>> = flow {
        val respond = client.get("$baseUrl/notes").body<List<Note>>()
        emit(respond)
    }

    fun getNote(id: String): Flow<Note> = flow {
        Logger.d("getNote $baseUrl/notes/$id")
        val respond = client.get("$baseUrl/notes/$id").body<Note>()
        emit(respond)
    }

}