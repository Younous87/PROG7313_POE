package com.example.prog7313_poe.data_access_object

import android.net.Uri
import androidx.room.TypeConverter

class UriTypeConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String?{
        return  uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri?{
        return uriString?.let {Uri.parse(it)}
    }
}