package com.example.nukanote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val noteRepository: NoteRepository
    val allNotes: LiveData<List<Note>>

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        noteRepository = NoteRepository(noteDao)
        allNotes = noteRepository.getAllNotes()
    }

    fun getNoteById(noteId: Int): LiveData<Note> {
        return noteRepository.getNoteById(noteId)
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            noteRepository.insert(note)
        }
    }
}
