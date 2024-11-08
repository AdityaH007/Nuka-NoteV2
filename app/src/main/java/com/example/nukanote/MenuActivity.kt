package com.example.nukanote

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import yuku.ambilwarna.AmbilWarnaDialog

class MenuActivity : AppCompatActivity() {

    private lateinit var textColorButton: Button
    private lateinit var headingColorButton: Button
    private lateinit var boldItalicsColorButton: Button
    private lateinit var backgroundColorButton: Button
    private lateinit var saveThemeButton: Button

    private var selectedTextColor: Int = Color.BLACK // Default black
    private var selectedHeadingColor: Int = Color.BLACK
    private var selectedBoldItalicsColor: Int = Color.BLACK
    private var selectedBackgroundColor: Int = Color.WHITE // Default white

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("theme_preferences", MODE_PRIVATE)

        // Initialize buttons
        textColorButton = findViewById(R.id.selectTextColorButton)
        headingColorButton = findViewById(R.id.selectHeadingColorButton)
        boldItalicsColorButton = findViewById(R.id.selectBoldItalicsColorButton)
        backgroundColorButton = findViewById(R.id.selectBackgroundColorButton)
        saveThemeButton = findViewById(R.id.saveThemeButton)

        // Load saved colors from preferences
        loadSavedThemeColors()

        // Set listeners for color selection
        textColorButton.setOnClickListener {
            showColorPickerDialog("Select Text Color") { color ->
                selectedTextColor = color
                textColorButton.setBackgroundColor(color)
            }
        }

        headingColorButton.setOnClickListener {
            showColorPickerDialog("Select Heading Color") { color ->
                selectedHeadingColor = color
                headingColorButton.setBackgroundColor(color)
            }
        }

        boldItalicsColorButton.setOnClickListener {
            showColorPickerDialog("Select Bold/Italics Color") { color ->
                selectedBoldItalicsColor = color
                boldItalicsColorButton.setBackgroundColor(color)
            }
        }

        backgroundColorButton.setOnClickListener {
            showColorPickerDialog("Select Background Color") { color ->
                selectedBackgroundColor = color
                backgroundColorButton.setBackgroundColor(color)
            }
        }

        // Save theme settings when the save button is clicked
        saveThemeButton.setOnClickListener {
            saveTheme()
            applyColorsFromPreferences()
            ThemeManager.saveTheme(this,selectedBackgroundColor)
        }
    }

    // Method to show color picker dialog
    private fun showColorPickerDialog(title: String, onColorSelected: (Int) -> Unit) {
        val colorPicker = AmbilWarnaDialog(this, Color.BLACK, object : AmbilWarnaDialog.OnAmbilWarnaListener {
            override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                onColorSelected(color)
            }

            override fun onCancel(dialog: AmbilWarnaDialog) {
                // User cancelled the color selection
            }
        })
        colorPicker.show()
    }

    // Save selected colors to SharedPreferences
    private fun saveTheme() {
        val editor = sharedPreferences.edit()
        editor.putInt("textColor", selectedTextColor)
        editor.putInt("headingColor", selectedHeadingColor)
        editor.putInt("boldItalicsColor", selectedBoldItalicsColor)
        editor.putInt("backgroundColor", selectedBackgroundColor)
        editor.apply()
        Toast.makeText(this, "Theme Saved!", Toast.LENGTH_SHORT).show()
    }

    // Load saved colors from SharedPreferences
    private fun loadSavedThemeColors() {
        selectedTextColor = sharedPreferences.getInt("textColor", Color.BLACK)
        selectedHeadingColor = sharedPreferences.getInt("headingColor", Color.BLACK)
        selectedBoldItalicsColor = sharedPreferences.getInt("boldItalicsColor", Color.BLACK)
        selectedBackgroundColor = sharedPreferences.getInt("backgroundColor", Color.WHITE)

        // Set buttons to display saved colors
        textColorButton.setBackgroundColor(selectedTextColor)
        headingColorButton.setBackgroundColor(selectedHeadingColor)
        boldItalicsColorButton.setBackgroundColor(selectedBoldItalicsColor)
        backgroundColorButton.setBackgroundColor(selectedBackgroundColor)

        // Apply colors to the main layout and other UI elements
        applyColorsFromPreferences()
    }

    private fun applyColorsFromPreferences() {
        findViewById<ConstraintLayout>(R.id.main).setBackgroundColor(selectedBackgroundColor)

        // Example: Set text colors dynamically if you have TextViews
       // findViewById<TextView>(R.id.toolbar_title)?.setTextColor(selectedHeadingColor)

        // Update other UI elements with selected colors as needed
    }
}
