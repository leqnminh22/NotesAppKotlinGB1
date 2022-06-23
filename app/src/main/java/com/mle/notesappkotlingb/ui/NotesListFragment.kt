package com.mle.notesappkotlingb.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.mle.notesappkotlingb.R
import com.mle.notesappkotlingb.databinding.FragmentNotesListBinding
import com.mle.notesappkotlingb.domain.Callback
import com.mle.notesappkotlingb.domain.Note
import com.mle.notesappkotlingb.domain.NotesRepositoryImpl
import com.mle.notesappkotlingb.domain.OnNoteClickListener

class NotesListFragment : Fragment(R.layout.fragment_notes_list) {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!

    private var selectedNote: Note? = null
    private var selectedPosition: Int = 0
    private var adapter: NotesAdapter = NotesAdapter(this, object : OnNoteClickListener{
        override fun onNoteClicked(note: Note) {
            Toast.makeText(requireContext(), note.title, Toast.LENGTH_SHORT).show()
        }

        override fun onLongNoteClicked(note: Note, position: Int) {
            selectedNote = note
            selectedPosition = position
        }

    })



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
            override fun onSuccess(data: List<Note>?) {
                if (data != null) {
                    adapter.setData(data)
                }
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception) {
                progressBar.visibility = View.GONE
            }
        })

        binding.btnAdd.setOnClickListener {
            NoteAddBottomDialogFragment().addInstance().show(parentFragmentManager, "Add Note")
        }

        parentFragmentManager.setFragmentResultListener(NoteAddBottomDialogFragment.ADD_NOTE_RESULT_KEY,
            viewLifecycleOwner, FragmentResultListener { requestKey, result ->
                val note: Note? = result.getParcelable(NoteAddBottomDialogFragment.ARG_NOTE)
                if (note != null) {
                    val index = adapter.addNote(note)
                    adapter.notifyItemInserted(index)
                }

            })


    }

    override fun onCreateContextMenu(
        menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)

        val menuInflater = requireActivity().menuInflater
        menuInflater.inflate(R.menu.menu_context, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {

            R.id.action_delete -> {
                binding.progressBar.visibility = View.VISIBLE
                selectedNote?.let {
                    NotesRepositoryImpl.remove(it, object : Callback<Unit> {
                        override fun onSuccess(data: Unit?) {
                            adapter.remove(selectedNote!!)
                            adapter.notifyItemRemoved(selectedPosition)

                            binding.progressBar.visibility = View.GONE
                        }

                        override fun onError(e: Exception) {
                            binding.progressBar.visibility = View.GONE
                        }

                    })
                }
                return true
            }
            R.id.action_edit -> {
                Toast.makeText(requireContext(), "Edited", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onContextItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}