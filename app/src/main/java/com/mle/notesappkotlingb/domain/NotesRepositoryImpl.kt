package com.mle.notesappkotlingb.domain


import android.os.Handler
import android.os.Looper
import java.util.ArrayList
import java.util.concurrent.Executors


object NotesRepositoryImpl : NotesRepository {

    private val data = ArrayList<Note>()
    private val executor = Executors.newSingleThreadExecutor()
    private val handler = Handler(Looper.getMainLooper())


    override fun getAll(callback: Callback<List<Note>>) {
        executor.execute(Runnable {
            Thread.sleep(2000L)
        })

        handler.post(Runnable {
            callback.onSuccess(data)
        })

    }
}