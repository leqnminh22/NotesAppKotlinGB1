package com.mle.notesappkotlingb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.mle.notesappkotlingb.R
import com.mle.notesappkotlingb.databinding.FragmentNotesListBinding
import com.mle.notesappkotlingb.domain.Callback
import com.mle.notesappkotlingb.domain.Note
import com.mle.notesappkotlingb.domain.NotesRepositoryImpl

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val adapter = NotesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notesList = binding.notesList

        notesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        notesList.adapter = adapter
        val progressBar = binding.progressBar
        progressBar.visibility = View.VISIBLE

        // получить список заметок
        NotesRepositoryImpl.getAll(object : Callback<List<Note>> {
            override fun onSuccess(data: List<Note>) {
                adapter.setData(data)
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception) {
                progressBar.visibility = View.GONE
            }
        })

        binding.btnAdd.setOnClickListener {
            NoteAddBottomDialogFragment().show(parentFragmentManager, "Add Note")
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}