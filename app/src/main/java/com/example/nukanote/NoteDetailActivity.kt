package com.example.nukanote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var noteDetailTitle: EditText
    private lateinit var noteDetailContent: EditText
    private lateinit var saveNoteButton: Button

    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        // Find views
        noteDetailTitle = findViewById(R.id.noteDetailTitle)
        noteDetailContent = findViewById(R.id.noteDetailContent)
        saveNoteButton = findViewById(R.id.saveNoteButton)

        // Retrieve the note ID from the intent
        val noteId = intent.getLongExtra("note_id", -1L)

        if (noteId != -1L) {
            // Observe the specific note by ID
            noteViewModel.getNoteById(noteId.toInt()).observe(this, Observer { note ->
                if (note != null) {
                    // Populate EditText fields with existing note data
                    noteDetailTitle.setText(note.title)
                    noteDetailContent.setText(note.content)
                } else {
                    noteDetailTitle.setText("Note not found")
                    noteDetailContent.setText("Unable to retrieve the note details.")
                }
            })
        }

        // Save button click listener
        saveNoteButton.setOnClickListener {
            val updatedTitle = noteDetailTitle.text.toString()
            val updatedContent = noteDetailContent.text.toString()

            if (updatedTitle.isNotEmpty() || updatedContent.isNotEmpty()) {
                // Save updated note
                val updatedNote = Note(
                    id = noteId.toInt(), // Use the existing note ID
                    title = updatedTitle,
                    content = updatedContent
                )

                noteViewModel.insert(updatedNote) // Update the note in the database
                finish() // Close the activity after saving
            }
        }
    }
}
