package com.mle.notesappkotlingb.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mle.notesappkotlingb.R
import com.mle.notesappkotlingb.databinding.ItemNoteBinding
import com.mle.notesappkotlingb.domain.Note
import com.mle.notesappkotlingb.domain.OnNoteClickListener
import java.text.SimpleDateFormat
import java.util.*


class NotesAdapter(private val fragment: Fragment = Fragment(), private val noteClicked: OnNoteClickListener) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val notesList = ArrayList<Note>()



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
        holder.binding.cardView.setOnClickListener {
            noteClicked.onNoteClicked(notesList[position])
        }

        fragment.registerForContextMenu(holder.binding.cardView)

        holder.binding.cardView.setOnLongClickListener {
            holder.binding.cardView.showContextMenu()
            noteClicked.onLongNoteClicked(notesList[position], position)
            return@setOnLongClickListener true
        }

    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun addNote(note: Note): Int {
        notesList.add(note)
        return notesList.size -1
    }

    fun remove(selectedNote: Note) {
        notesList.remove(selectedNote)
    }

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val simpleDate: SimpleDateFormat = SimpleDateFormat("HH:mm, dd.MM.yyyy", Locale.getDefault())

        val binding = ItemNoteBinding.bind(itemView)

        fun bind(note: Note) = with(binding) {
            noteTitle.text = note.title
            noteMessage.text = note.message
            noteDate.text = simpleDate.format(note.createdAt)


        }
    }

}
