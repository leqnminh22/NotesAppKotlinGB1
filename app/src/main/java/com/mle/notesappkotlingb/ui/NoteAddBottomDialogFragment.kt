package com.mle.notesappkotlingb.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.mle.notesappkotlingb.R

class NoteAddBottomDialogFragment: BottomSheetDialogFragment() {

    lateinit var title: TextInputEditText
    lateinit var message: TextInputEditText
    lateinit var btnSave: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)


    }

    fun init(view: View) {
        title = view.findViewById(R.id.title)
        message = view.findViewById(R.id.message)
        btnSave = view.findViewById(R.id.btnSave)
    }
}