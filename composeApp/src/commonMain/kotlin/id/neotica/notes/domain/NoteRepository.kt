package id.neotica.notes.domain

import id.neotica.notes.domain.model.ApiResult
import id.neotica.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNotes(): Flow<ApiResult<List<Note>>>
    fun getNote(id: String): Flow<ApiResult<Note>>
    fun postNote(note: Note): Flow<ApiResult<Note>>
    fun updateNote(noteId: String, note: Note): Flow<ApiResult<Note>>
    fun deleteNote(noteId: String): Flow<ApiResult<String>>
}