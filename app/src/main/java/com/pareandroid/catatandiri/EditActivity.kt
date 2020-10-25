package com.pareandroid.catatandiri

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.pareandroid.catatandiri.database.NoteDao
import com.pareandroid.catatandiri.database.RoomDatabase
import com.pareandroid.catatandiri.model.NoteModel
import com.thebluealliance.spectrum.SpectrumPalette
import kotlinx.android.synthetic.main.activity_edit.*
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {

    companion object{
        const val  EXTRA_NOTE = "extra_note"
    }

    private lateinit var database : RoomDatabase
    private lateinit var dao: NoteDao
    var colors :Int = 0
    private lateinit var note: NoteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        database = RoomDatabase.getDatabase(applicationContext)!!
        dao = database.noteDao()!!

        readMode()

        val itemNote = intent.getParcelableExtra(EXTRA_NOTE) as NoteModel
        var date_n = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())

        editPalette.setOnColorSelectedListener { color ->colors = color  }
        edt_titleEdit.setText(itemNote.judul)
        edt_noteEdit.setText(itemNote.catatan)
        editPalette.setSelectedColor(itemNote.color)

        btn_change.setOnClickListener {
            editMode()
        }

        btn_edit.setOnClickListener {
            val judul = edt_titleEdit.text.toString()
            val catatan = edt_noteEdit.text.toString()
            val warna = this.colors
            if (judul.isEmpty()&&catatan.isEmpty())
            {
                edt_titleEdit.error="Jangan Kosongkan Field"
                edt_noteEdit.error="Jangan Kosongkan Field"
            }else{
                update(NoteModel(id = itemNote.id,judul = judul,catatan = catatan,tanggal = date_n,color = warna))
                val intent = Intent(this,MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
        }

        btn_delete.setOnClickListener {
            val mAlertBuilder = AlertDialog.Builder(this@EditActivity)
            mAlertBuilder.setTitle("Hapus Data")
            mAlertBuilder.setMessage("Anda Yakin Ingin Hapus ?")
            mAlertBuilder.setCancelable(false)

            mAlertBuilder.apply {
                setPositiveButton("Ya"){dialogInterface, i ->
                    deleteNote(itemNote)

                    val intent = Intent(this@EditActivity,MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                    finish()
                }

                setNegativeButton("Tidak"){dialogInterface, i ->
                    Toast.makeText(this@EditActivity, "Cancel", Toast.LENGTH_SHORT).show()
                }
            }

            val alertDialog = mAlertBuilder.create()
            alertDialog.show()

        }
    }

    private fun update (note : NoteModel)
    {
        dao.update(note)
        Toast.makeText(this@EditActivity,"Berhasil Update",Toast.LENGTH_SHORT).show()
    }

    private fun deleteNote(note : NoteModel)
    {
        dao.delete(note)
        Toast.makeText(this@EditActivity,"Catatan Berhasil Di hapus",Toast.LENGTH_SHORT).show()
    }

    private fun editMode()
    {
        btn_change.visibility = View.GONE
        btn_edit.visibility = View.VISIBLE
        btn_delete.visibility = View.VISIBLE
        edt_titleEdit.isFocusableInTouchMode = true
        edt_noteEdit.isFocusableInTouchMode = true
        editPalette.isFocusableInTouchMode = true
    }

    private fun readMode()
    {
        edt_titleEdit.isFocusableInTouchMode = false
        edt_noteEdit.isFocusableInTouchMode = false
        edt_titleEdit.isFocusable = false
        edt_noteEdit.isFocusable = false
        editPalette.isEnabled = false
    }


}
