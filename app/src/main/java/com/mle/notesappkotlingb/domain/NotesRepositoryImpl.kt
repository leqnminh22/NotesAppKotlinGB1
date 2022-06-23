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

        data.add(Note(UUID.randomUUID().toString(), "Title 1", "Message 1", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 2", "Message 2", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 3", "Message 3", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 4", "Message 4", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 5", "Message 5", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 6", "Message 6", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 7", "Message 7", Date()))
        data.add(Note(UUID.randomUUID().toString(), "Title 8", "Message 8", Date()))

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
}