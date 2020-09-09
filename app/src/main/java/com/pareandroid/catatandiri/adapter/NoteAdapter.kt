package com.pareandroid.catatandiri.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pareandroid.catatandiri.EditActivity
import com.pareandroid.catatandiri.R
import com.pareandroid.catatandiri.model.NoteModel
import kotlinx.android.synthetic.main.item_note.view.*

class NoteAdapter () :RecyclerView.Adapter<NoteAdapter.NoteViewHolder>(),Filterable {

    private var mListNote : MutableList<NoteModel> = ArrayList()
    private var mFilterNote : MutableList<NoteModel> = ArrayList()

    fun updateAdapter(mData : List<NoteModel>){
        if (mListNote == null){
            mListNote.addAll(mData)
            mFilterNote.addAll(mData)
            notifyItemChanged(0,mFilterNote.size)
        }else{
            val result = DiffUtil.calculateDiff(object : DiffUtil.Callback(){
                override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    return mListNote.get(oldItemPosition).judul===mData[newItemPosition].judul
                }

                override fun getOldListSize(): Int {
                    return mListNote.size
                }

                override fun getNewListSize(): Int {
                   return  mData.size
                }

                override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                    val newDataModel = mListNote.get(oldItemPosition)
                    val oldDataModel = mData[newItemPosition]
                    return newDataModel.judul === oldDataModel.judul
                }
            })
            mListNote.addAll(mData)
            mFilterNote.addAll(mData)
            result.dispatchUpdatesTo(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note,parent,false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mListNote != null){
            mFilterNote.size
        }else{
            0
        }
    }

    override fun onBindViewHolder(holder: NoteAdapter.NoteViewHolder, position: Int) {

        holder.itemView.apply {
            mFilterNote[position].apply {
                tv_tittle.text = judul
                tv_note.text = catatan
                tv_date.text = "Last Changed : "+tanggal

                card_item.setCardBackgroundColor(color)

                setOnClickListener {

                    val intent = Intent(holder.itemView.context,EditActivity::class.java)
                    intent.putExtra(EditActivity.EXTRA_NOTE,mFilterNote[position])
                    holder.itemView.context.startActivity(intent)
                }
            }

        }
    }


    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
               val charString : String = p0.toString()
                if (charString.isEmpty()){
                    mFilterNote = mListNote
                }else{
                    val filteredList : ArrayList<NoteModel> = ArrayList()
                    for (item in mListNote){
                        if ( item.judul.toLowerCase().contains(charString.toLowerCase())){
                            filteredList.add(item)
                        }
                    }
                    mFilterNote = filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = mFilterNote
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
               mFilterNote = p1?.values as MutableList<NoteModel>
                notifyDataSetChanged()
            }

        }
    }

    fun clear()
    {
        mFilterNote.clear()
    }


}
