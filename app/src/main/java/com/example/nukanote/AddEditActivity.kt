package com.example.nukanote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import io.noties.markwon.Markwon

class AddEditActivity : AppCompatActivity() {

    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        applyTheme()

        // Observe theme changes
        ThemeManager.themeChanged.observe(this, Observer {
            applyTheme()
        })

        titleEditText = findViewById(R.id.noteTitleEditText)
        contentEditText = findViewById(R.id.noteContentEditText)
        saveButton = findViewById(R.id.saveNoteButton)
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Initialize Markwon for rendering Markdown content
        val markwon = Markwon.create(this)

        // Set up a listener to show the formatted content as the user types
        contentEditText.addTextChangedListener {
            val markdownContent = contentEditText.text.toString()
        }

        saveButton.setOnClickListener {
            saveNote()
        }
    }


    private fun applyTheme() {
        // Apply the background color to the root view
        findViewById<android.view.View>(android.R.id.content).setBackgroundColor(
            ThemeManager.getBackgroundColor(this)
        )
        // Remove recreate() as it's not necessary just for background color changes
        // and can cause a jarring user experience
    }

    private fun saveNote() {
        val title = titleEditText.text.toString()
        val content = contentEditText.text.toString()

        // Validate input to make sure note is not empty
        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Save the note using ViewModel
        val note = Note(title = title, content = content)
        noteViewModel.insert(note)

        Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        finish()
    }
}
