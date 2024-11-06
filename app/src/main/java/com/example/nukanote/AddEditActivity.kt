package com.example.nukanote

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddEditActivity : AppCompatActivity() {


    private lateinit var titleEditText: EditText
    private lateinit var contentEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        titleEditText = findViewById(R.id.noteTitleEditText)
        contentEditText = findViewById(R.id.noteContentEditText)
        saveButton = findViewById(R.id.saveNoteButton)

        saveButton.setOnClickListener {
            saveNote()
        }
    }

    private fun saveNote() {
        val title = titleEditText.text.toString()
        val content = contentEditText.text.toString()

        // Validate input to make sure note is not empty
        if (title.isEmpty() && content.isEmpty()) {
            Toast.makeText(this, "Note cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }

        // Code to save the note locally using Room database (to be added)
        // Later, we can sync this with Firebase Firestore.

        // Navigate back to the main screen
        finish()
    }
}