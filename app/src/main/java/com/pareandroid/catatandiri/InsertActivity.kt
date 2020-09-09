package com.pareandroid.catatandiri

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pareandroid.catatandiri.database.NoteDao
import com.pareandroid.catatandiri.database.RoomDatabase
import com.pareandroid.catatandiri.model.NoteModel
import com.thebluealliance.spectrum.SpectrumPalette
import kotlinx.android.synthetic.main.activity_insert.*
import java.text.SimpleDateFormat
import java.util.*

class InsertActivity : AppCompatActivity() {
    private lateinit var database : RoomDatabase
    private lateinit var dao: NoteDao
    var colors :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        database = RoomDatabase.getDatabase(applicationContext)!!
        dao = database.noteDao()!!
        palette.setOnColorSelectedListener(SpectrumPalette.OnColorSelectedListener { color ->colors  = color  })
        var date_n = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date()) //get hold of textview.
        btn_save.setOnClickListener {
            val judul = edt_tittle.text.toString()
            val catatan = edt_note.text.toString()
            val warna = this.colors
            if ( judul.isEmpty()&& catatan.isEmpty()){
                edt_note.error = "Mohon Isikan Judul dan Catatan"
                edt_tittle.error = "Mohon Isikan Judul dan Catatan"
            }else{
                saveNote(NoteModel(judul = judul,catatan = catatan,tanggal = date_n,color = warna))
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun saveNote (note : NoteModel)
    {
        dao.insert(note)
        Toast.makeText(this@InsertActivity,"Berhasil", Toast.LENGTH_SHORT).show()
    }

}
