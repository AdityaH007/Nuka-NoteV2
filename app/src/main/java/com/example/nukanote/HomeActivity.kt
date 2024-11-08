package com.example.nukanote

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels()
    private val noteAdapter by lazy {
        NoteAdapter { note -> openNoteDetail(note.id.toLong()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Apply background color initially
        applyTheme()

        // Observe theme changes
        ThemeManager.themeChanged.observe(this, Observer {
            applyTheme()
        })

        setupRecyclerView()
        setupFABs()

        noteViewModel.allNotes.observe(this) { notes ->
            noteAdapter.submitList(notes)
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

    private fun setupRecyclerView() {
        findViewById<RecyclerView>(R.id.notesRecyclerView).apply {
            adapter = noteAdapter
            layoutManager = LinearLayoutManager(this@HomeActivity)
        }
    }

    private fun setupFABs() {
        findViewById<FloatingActionButton>(R.id.addNoteFab).setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
        findViewById<FloatingActionButton>(R.id.addNoteFab2).setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
    }

    private fun openNoteDetail(noteId: Long) {
        val intent = Intent(this, NoteDetailActivity::class.java)
        intent.putExtra("note_id", noteId)
        startActivity(intent)
    }
}