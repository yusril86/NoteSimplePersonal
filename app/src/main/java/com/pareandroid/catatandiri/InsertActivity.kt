package com.pareandroid.catatandiri

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pareandroid.catatandiri.database.NoteDao
import com.pareandroid.catatandiri.database.RoomDatabase
import com.pareandroid.catatandiri.model.NoteModel
import com.thebluealliance.spectrum.SpectrumPalette
import kotlinx.android.synthetic.main.activity_insert.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class InsertActivity : AppCompatActivity() {
    private lateinit var database : RoomDatabase
    private lateinit var dao: NoteDao
    var SELECT_FILE : Int = 0
    var colors :Int = 0
    lateinit var uriImage : Uri
    private val BASE_IMAGE_URL = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        //Database init
        database = RoomDatabase.getDatabase(applicationContext)!!
        dao = database.noteDao()!!


        palette.setOnColorSelectedListener(SpectrumPalette.OnColorSelectedListener { color ->colors  = color  })
        var date_n = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date()) //get hold of textview.

        /*val cal = Calendar.getInstance()
        val file = File(applicationContext.filesDir, "/Backup/" + (cal.timeInMillis.toString() + ".jpg"))
        uriImage = Uri.fromFile(file)*/

        uriImage = Uri.parse(BASE_IMAGE_URL)

        btn_addImage.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE   ) == PackageManager.PERMISSION_DENIED){
                    val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    requestPermissions(permission, SELECT_FILE)
                }else{
                    selectImage()
                }
            }
        }

        btn_save.setOnClickListener {
            val judul = edt_tittle.text.toString()
            val catatan = edt_note.text.toString()
            val image = uriImage.toString()
            val warna = this.colors
            if ( judul.isEmpty()&& catatan.isEmpty()){
                edt_note.error = "Mohon Isikan Judul dan Catatan"
                edt_tittle.error = "Mohon Isikan Judul dan Catatan"
            }else{
                saveNote(NoteModel(judul = judul,catatan = catatan,tanggal = date_n, image = image, color = warna))
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



    private fun selectImage(){

//        val intentImage = Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        val intentImage = Intent(Intent.ACTION_GET_CONTENT)
//        intentImage.type = "image/*"

        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).also {
            it.addCategory(Intent.CATEGORY_OPENABLE)
            it.type = "image/*"
            it.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
            it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivityForResult(Intent.createChooser(it,"SELECT PICTURE.."),SELECT_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if ( resultCode == Activity.RESULT_OK && requestCode == SELECT_FILE){
            if (data != null) {
               uriImage = data.data!!
                iv_imageNote.setImageURI(uriImage)
          }
        }else{
            Toast.makeText(this@InsertActivity, "Fail Load Image", Toast.LENGTH_SHORT).show()
        }
    }





}
