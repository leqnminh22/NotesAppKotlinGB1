package com.mle.notesappkotlingb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mle.notesappkotlingb.R
import com.mle.notesappkotlingb.domain.Callback
import com.mle.notesappkotlingb.domain.Note
import com.mle.notesappkotlingb.domain.NotesRepositoryImpl

class NoteAddBottomDialogFragment : BottomSheetDialogFragment() {

    lateinit var title: TextInputEditText
    lateinit var message: TextInputEditText
    lateinit var btnSave: MaterialButton
    private var noteToEdit: Note? = null

    companion object {
        const val ARG_NOTE: String = "ARG_NOTE"
        const val ADD_NOTE_RESULT_KEY: String = "ADD_NOTE_RESULT_KEY"
        const val EDIT_NOTE_RESULT_KEY: String = "EDIT_NOTE_RESULT_KEY"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    fun addInstance() = NoteAddBottomDialogFragment().apply {
        val args = Bundle()

        val fragment = NoteAddBottomDialogFragment()
        fragment.arguments = args
        return fragment
    }

    fun editInstance(note: Note) = NoteAddBottomDialogFragment().apply{
        val args = Bundle()
        args.putParcelable(ARG_NOTE, note)

        val fragment = NoteAddBottomDialogFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)


        if(requireArguments().containsKey(ARG_NOTE)) {
            noteToEdit = arguments?.getParcelable(ARG_NOTE)
        }
        if(noteToEdit != null) {
            title.setText(noteToEdit?.title)
            message.setText(noteToEdit?.message)
        }

        val finalNoteToEdit = noteToEdit

        btnSave.setOnClickListener {
            btnSave.isEnabled = false

            if(finalNoteToEdit != null){
                NotesRepositoryImpl.edit(finalNoteToEdit, title.text.toString(), message.text.toString(), object : Callback<Note> {
                    override fun onSuccess(data: Note?) {

                        val bundle = Bundle()
                        bundle.putParcelable(ARG_NOTE, data)
                        parentFragmentManager.setFragmentResult(EDIT_NOTE_RESULT_KEY, bundle)
                        btnSave.isEnabled = true
                        dismiss()
                    }

                    override fun onError(e: Exception) {

                        btnSave.isEnabled = true

                    }

                })

            } else{
                NotesRepositoryImpl.add(
                    title.text.toString(),
                    message.text.toString(),
                    object : Callback<Note> {
                        override fun onSuccess(data: Note?) {
                            btnSave.isEnabled = true

                            val bundle = Bundle()
                            bundle.putParcelable(ARG_NOTE, data)

                            parentFragmentManager.setFragmentResult(ADD_NOTE_RESULT_KEY, bundle)
                            dismiss()

                        }

                        override fun onError(e: Exception) {
                            btnSave.isEnabled = true
                        }

                    })
            }

        }


    }

    private fun init(view: View) {
        title = view.findViewById(R.id.title)
        message = view.findViewById(R.id.message)
        btnSave = view.findViewById(R.id.btnSave)
    }
}