package com.pareandroid.catatandiri.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "notes")

@Parcelize
data class NoteModel(
    @PrimaryKey(autoGenerate = true)@ColumnInfo(name = "id") var id :Int = 0,
    @ColumnInfo(name = "judul") var judul : String = "",
    @ColumnInfo(name = "catatan") var catatan : String = "",
    @ColumnInfo(name = "tanggal") var tanggal : String = "",
    @ColumnInfo(name = "color") var color : Int = 0
):Parcelable
    
