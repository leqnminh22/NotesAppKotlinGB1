package com.mle.notesappkotlingb.domain

interface NotesRepository {
    fun getAll(callback: Callback<List<Note>>)
}