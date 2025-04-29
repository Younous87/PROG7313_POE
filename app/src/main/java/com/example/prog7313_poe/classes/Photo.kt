package com.example.prog7313_poe.classes

import android.net.Uri

data class Photo(
    val photoID : String? = null,
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