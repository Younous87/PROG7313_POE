package com.example.prog7313_poe.classes

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

data class Photo(
    var photoID : String? = null,
    var filename : String? = null,
    var fileUri: String? = null
){
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Insert photo into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
//    fun createPhoto(uri: Uri, filename: String?){
//        val photo = Photo("",filename,uri)
//    }

}