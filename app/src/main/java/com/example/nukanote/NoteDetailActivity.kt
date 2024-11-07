package com.example.nukanote

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var noteDetailTitle: TextView
    private lateinit var noteDetailContent: TextView

    // Use ViewModel delegation to obtain the NoteViewModel
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        // Find views
        noteDetailTitle = findViewById(R.id.noteDetailTitle)
        noteDetailContent = findViewById(R.id.noteDetailContent)

        // Retrieve the note ID from the intent
        val noteId = intent.getLongExtra("note_id", -1)

        if (noteId != -1L) {
            // Observe the specific note by ID
            noteViewModel.getNoteById(noteId.toInt()).observe(this, Observer { note ->
                if (note != null) {
                    noteDetailTitle.text = note.title
                    noteDetailContent.text = note.content
                }
            })
        } else {
            // Handle the case where the note ID is invalid (optional)
            noteDetailTitle.text = "Error"
            noteDetailContent.text = "Could not load note."
        }
    }
}
