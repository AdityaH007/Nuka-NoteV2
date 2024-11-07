package com.example.nukanote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import io.noties.markwon.Markwon

class NoteDetailActivity : AppCompatActivity() {

    private lateinit var noteDetailTitle: EditText
    private lateinit var noteDetailContent: EditText
    private lateinit var noteDetailTitleView: TextView
    private lateinit var noteDetailContentView: TextView
    private lateinit var saveNoteButton: Button
    private lateinit var viewEditButton: Button

    private val noteViewModel: NoteViewModel by viewModels()

    private var isEditMode = false  // Flag to check whether we are in Edit mode or View mode

    private lateinit var markwon: Markwon  // Declare Markwon for Markdown rendering

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_detail)

        // Initialize Markwon after setContentView
        markwon = Markwon.create(this)

        // Find views
        noteDetailTitle = findViewById(R.id.noteDetailTitle)
        noteDetailContent = findViewById(R.id.noteDetailContent)
        noteDetailTitleView = findViewById(R.id.noteDetailTitleView)
        noteDetailContentView = findViewById(R.id.noteDetailContentView)
        saveNoteButton = findViewById(R.id.saveNoteButton)
        viewEditButton = findViewById(R.id.viewEditButton)

        // Retrieve the note ID from the intent
        val noteId = intent.getLongExtra("note_id", -1L)

        if (noteId != -1L) {
            // Observe the specific note by ID
            noteViewModel.getNoteById(noteId.toInt()).observe(this, Observer { note ->
                if (note != null) {
                    // Populate fields with existing note data
                    noteDetailTitle.setText(note.title)
                    noteDetailContent.setText(note.content)
                    noteDetailTitleView.text = note.title
                    setMarkdownContent(note.content)  // Render markdown content
                    setViewMode()  // Default mode is view
                } else {
                    noteDetailTitle.setText("Note not found")
                    noteDetailContent.setText("Unable to retrieve the note details.")
                }
            })
        }

        // Save Button click listener to save note
        saveNoteButton.setOnClickListener {
            val updatedTitle = noteDetailTitle.text.toString()
            val updatedContent = noteDetailContent.text.toString()

            if (updatedTitle.isNotEmpty() || updatedContent.isNotEmpty()) {
                val updatedNote = Note(
                    id = intent.getLongExtra("note_id", -1L).toInt(),
                    title = updatedTitle,
                    content = updatedContent
                )
                noteViewModel.insert(updatedNote) // Save the note to database
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            }
        }

        // View/Edit button click listener
        viewEditButton.setOnClickListener {
            if (isEditMode) {
                // Switch to view mode
                setViewMode()
            } else {
                // Switch to edit mode
                setEditMode()
            }
        }
    }

    private fun setViewMode() {
        // Show the content in text views and disable editing
        noteDetailTitleView.visibility = TextView.VISIBLE
        noteDetailContentView.visibility = TextView.VISIBLE
        noteDetailTitle.visibility = EditText.GONE
        noteDetailContent.visibility = EditText.GONE

        // Disable editing
        noteDetailTitleView.text = noteDetailTitle.text
        noteDetailContentView.text = noteDetailContent.text

        // Render markdown content in View mode
        setMarkdownContent(noteDetailContent.text.toString())

        // Show the buttons
        viewEditButton.text = "Edit"
        saveNoteButton.visibility = Button.VISIBLE

        isEditMode = false
    }

    private fun setEditMode() {
        // Hide text views and show edit fields
        noteDetailTitleView.visibility = TextView.GONE
        noteDetailContentView.visibility = TextView.GONE
        noteDetailTitle.visibility = EditText.VISIBLE
        noteDetailContent.visibility = EditText.VISIBLE

        // Enable editing
        viewEditButton.text = "View"
        saveNoteButton.visibility = Button.VISIBLE

        isEditMode = true
    }

    private fun setMarkdownContent(content: String) {
        // Use Markwon to render Markdown content into the TextView
        markwon.setMarkdown(noteDetailContentView, content)
    }
}
