package com.pareandroid.catatandiri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pareandroid.catatandiri.adapter.NoteAdapter
import com.pareandroid.catatandiri.database.NoteDao
import com.pareandroid.catatandiri.database.RoomDatabase
import com.pareandroid.catatandiri.model.NoteModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getData()
        btn_add.setOnClickListener {
            val intent = Intent(this@MainActivity,InsertActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData ()
    {
        val database = RoomDatabase.getDatabase(applicationContext)
        val dao = database?.noteDao()
        val listItem = arrayListOf<NoteModel>()
        listItem.addAll(dao!!.getAll())
        setAdapter(listItem)
    }


    private fun setAdapter(list : ArrayList<NoteModel>){
        rv_note.adapter = NoteAdapter(list)
    }

    override fun onResume() {
        super.onResume()
        getData()
    }
}
