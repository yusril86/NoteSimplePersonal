package com.pareandroid.catatandiri.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pareandroid.catatandiri.EditActivity
import com.pareandroid.catatandiri.R
import com.pareandroid.catatandiri.model.NoteModel
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter (private val listItem : ArrayList<NoteModel>) :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listItem.size
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {
        val item = listItem[position]
        holder.itemView.apply {
            tv_tittle.setText(item.judul)
            tv_note.setText(item.catatan)
            tv_date.setText("Last Changed : "+item.tanggal)

            card_item.setCardBackgroundColor(item.color)

            setOnClickListener {

                val intent = Intent(holder.itemView.context,EditActivity::class.java)
                intent.putExtra(EditActivity.EXTRA_NOTE,item)
                holder.itemView.context.startActivity(intent)
            }
        }
    }


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}
