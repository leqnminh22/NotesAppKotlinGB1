package com.mle.notesappkotlingb.domain

interface Callback<T> {
    fun onSuccess(data: T)
    fun onError(e: Exception)
}