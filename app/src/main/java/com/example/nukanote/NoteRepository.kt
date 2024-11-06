package com.example.nukanote

import androidx.lifecycle.LiveData

// NoteRepository.kt
class NoteRepository(private val noteDao: NoteDao) {
    fun getAllNotes(): LiveData<List<Note>> = noteDao.getAllNotes()

    fun getNoteById(id: Int): LiveData<Note> {
        return noteDao.getNoteById(id)
    }

    // In NoteRepository.kt
    fun getNoteById(noteId: Long) = noteDao.getNoteById(noteId)

}
