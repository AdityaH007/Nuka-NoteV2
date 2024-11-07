package com.example.nukanote

import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun getNoteById(noteId: Int): LiveData<Note> {
        return noteDao.getNoteById(noteId)
    }

    suspend fun insert(note: Note) {
        withContext(Dispatchers.IO) {
            noteDao.insert(note)
        }
    }
}
