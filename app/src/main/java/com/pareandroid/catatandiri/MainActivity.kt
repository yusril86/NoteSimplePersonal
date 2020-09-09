package com.pareandroid.catatandiri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import com.pareandroid.catatandiri.adapter.NoteAdapter
import com.pareandroid.catatandiri.database.NoteDao
import com.pareandroid.catatandiri.database.RoomDatabase
import com.pareandroid.catatandiri.model.NoteModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var adapterNote : NoteAdapter = NoteAdapter()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        adapterNote.clear()
        getData()
        btn_add.setOnClickListener {
            val intent = Intent(this@MainActivity,InsertActivity::class.java)
            startActivity(intent)
        }
        search_view.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
               adapterNote.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
               adapterNote.filter.filter(p0)
                return false
            }
        })
    }

    private fun getData ()
    {

        val database = RoomDatabase.getDatabase(applicationContext)
        val dao = database?.noteDao()
        adapterNote.clear()
//        val listItem = arrayListOf<NoteModel>()
        val listItem = dao!!.getAll()
        Log.d("tampil", "getData:"+dao!!.getAll())
        rv_note.apply {
            setHasFixedSize(true)
            adapter = adapterNote
        }
        /*if(listItem.addAll(dao!!.getAll())){
            setAdapter()
        }*/
//        setAdapter()
        adapterNote.updateAdapter(listItem)

    }


    private fun setAdapter(){
        rv_note.adapter = adapterNote
        rv_note.addItemDecoration(DividerItemDecoration(this@MainActivity,DividerItemDecoration.VERTICAL))
    }

    override fun onResume() {
        super.onResume()
//        getData()
        adapterNote.notifyDataSetChanged()
    }


}
