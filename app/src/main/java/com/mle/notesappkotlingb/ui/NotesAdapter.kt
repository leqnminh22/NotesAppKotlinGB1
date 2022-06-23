package com.mle.notesappkotlingb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mle.notesappkotlingb.R
import com.mle.notesappkotlingb.databinding.ItemNoteBinding
import com.mle.notesappkotlingb.domain.Note
import java.text.SimpleDateFormat
import java.util.*


class NotesAdapter() : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {


    private val simpleDate: SimpleDateFormat = SimpleDateFormat("dd.MM, HH.mm", Locale.getDefault())
    private val notesList = ArrayList<Note>()

    interface OnNoteClickListener {
        fun onNoteClicked(note: Note)
    }
    lateinit var noteClicked: OnNoteClickListener

    fun setData(notes: Collection<Note>) {
        notesList.addAll(notes)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)

        return NotesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.bind(notesList[position])
        holder.binding.cardView.setOnClickListener{
            noteClicked.onNoteClicked(notesList[position])
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemNoteBinding.bind(itemView)

        fun bind(note: Note) = with(binding) {
            noteTitle.text = note.title
            noteMessage.text = note.message
            noteDate.text = note.createdAt.toString()
        }
    }

}
