package com.mle.notesappkotlingb.domain

interface OnNoteClickListener {
    fun onNoteClicked(note: Note)
    fun onLongNoteClicked(note: Note, position: Int)
}