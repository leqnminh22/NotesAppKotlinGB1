package com.mle.notesappkotlingb.domain

interface NotesRepository {
    fun getAll(callback: Callback<List<Note>>)
    fun add(title: String, message: String, callback: Callback<Note>)
}