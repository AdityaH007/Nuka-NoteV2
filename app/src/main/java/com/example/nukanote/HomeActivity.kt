package com.example.nukanote

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private lateinit var noteViewModel: NoteViewModel
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var notesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize RecyclerView and Adapter
        notesRecyclerView = findViewById(R.id.notesRecyclerView)

        noteAdapter = NoteAdapter { note ->
            // Open NoteDetailActivity and pass the note ID
            val intent = Intent(this, NoteDetailActivity::class.java)
            intent.putExtra("note_id", note.id)
            startActivity(intent)
        }

        // Set up RecyclerView
        notesRecyclerView.adapter = noteAdapter
        notesRecyclerView.layoutManager = LinearLayoutManager(this)

        // Floating Action Button to Add Notes
        val addNoteFAB: FloatingActionButton = findViewById(R.id.addNoteFab)
        addNoteFAB.setOnClickListener {
            // Navigate to AddEditNoteActivity for creating a new note
            val intent = Intent(this, AddEditActivity::class.java)
            startActivity(intent)
        }

        // Initialize ViewModel
        noteViewModel = ViewModelProvider(this)[NoteViewModel::class.java]

        // Observe changes in the notes list and update the adapter
        noteViewModel.allNotes.observe(this, { notes ->
            notes?.let {
                noteAdapter.submitList(it)  // Update the adapter with the new notes list
            }
        })
    }
}
