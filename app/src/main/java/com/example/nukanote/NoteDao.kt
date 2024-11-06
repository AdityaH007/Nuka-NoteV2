package com.example.nukanote

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

// NoteDao.kt
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(note: Note)


    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNoteById(noteId: Int): LiveData<Note>

    // In NoteDao.kt
    @Query("SELECT * FROM note WHERE id = :noteId")
    fun getNoteById(noteId: Long): LiveData<Note>

}
