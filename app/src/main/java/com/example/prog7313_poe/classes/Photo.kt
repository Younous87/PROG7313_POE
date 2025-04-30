package com.example.prog7313_poe.classes

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val photoID : String,
    val filename : String? = null,
    val fileUri: Uri? = null
){
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Insert photo into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
    fun createPhoto(uri: Uri, filename: String?){
        val photo = Photo("",filename,uri)
    }

}