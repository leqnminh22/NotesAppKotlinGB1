package com.mle.notesappkotlingb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mle.notesappkotlingb.databinding.ActivityMainBinding
import com.mle.notesappkotlingb.ui.NotesInfoFragment
import com.mle.notesappkotlingb.ui.NotesListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            showNotes()
        }

        binding.bottomNav.setOnItemSelectedListener { view ->
            when(view.itemId) {
                R.id.action_notes -> {
                    showNotes()
                    true
                }
                R.id.action_info -> {
                    showNotesInfo()
                    true
                }
                else -> false
            }
        }
    }

    private fun showNotes(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, NotesListFragment())
            .commit()
    }

    private fun showNotesInfo() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, NotesInfoFragment())
            .commit()
    }
}