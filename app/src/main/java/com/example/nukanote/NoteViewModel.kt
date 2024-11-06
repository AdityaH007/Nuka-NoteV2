package com.example.nukanote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

// NoteViewModel.kt
class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.getAllNotes()
    }

    fun getNoteById(id: Int): LiveData<Note> {
        return noteRepository.getNoteById(id)
    }

    // In NoteViewModel.kt
    fun getNoteById(noteId: Long) = noteRepository.getNoteById(noteId)

}
