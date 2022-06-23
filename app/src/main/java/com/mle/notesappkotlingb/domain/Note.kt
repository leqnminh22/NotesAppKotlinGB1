package com.mle.notesappkotlingb.domain

import android.os.Parcelable
import java.util.*

@kotlinx.parcelize.Parcelize

data class Note(val id: String?, val title: String?, val message: String?, val createdAt: Date) : Parcelable {

}