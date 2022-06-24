package com.mle.notesappkotlingb.domain


import android.os.Handler
import android.os.Looper
import java.util.*
import java.util.concurrent.Executors


object NotesRepositoryImpl : NotesRepository {

    private val data = ArrayList<Note>()
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())

    init {

        for (i in 1..5) {
            data.add(Note(UUID.randomUUID().toString(), "Title 1", "Message 1", Date()))
        }

    }


    override fun getAll(callback: Callback<List<Note>>) {
        executor.execute(Runnable {
            Thread.sleep(2000L)
        })

        handler.post(Runnable {
            callback.onSuccess(data)
        })

    }

    override fun add(title: String, message: String, callback: Callback<Note>) {
        executor.execute(Runnable {
            Thread.sleep(1500L)
        })
        val note = Note(UUID.randomUUID().toString(), title, message, Date())
        data.add(note)
        handler.post(Runnable {

            callback.onSuccess(note)
        })
    }

    override fun remove(note: Note, callback: Callback<Unit>) {
        executor.execute(Runnable {
            Thread.sleep(1500L)
        })

        data.remove(note)
        handler.post(Runnable {
            callback.onSuccess(null)
        })
    }

    override fun edit(note: Note, title: String, message: String, callback: Callback<Note>) {
        executor.execute(Runnable {
            Thread.sleep(1500L)
        })



        val editedNote = Note(note.id, title, message, note.createdAt)
        val index = data.indexOf(note)
        data[index] = editedNote

        handler.post(Runnable {
            callback.onSuccess(editedNote)
        })
    }
}