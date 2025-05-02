package com.example.prog7313_poe.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.prog7313_poe.DataBase
import com.example.prog7313_poe.classes.Photo
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.data_access_object.PhotoDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel (application: Application) : AndroidViewModel(application) {
    private var photoDAO : PhotoDAO

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    init {
        val db = Room.databaseBuilder(
            application,
            DataBase::class.java,
            "DataBase"
        ).build()
        photoDAO = db.pDao

    }
    fun insertPhoto(photo : Photo) = viewModelScope.launch {
        photoDAO.insertPhoto(photo)
    }

    suspend fun getPhotoIdByFilenameAndUri(filename: String,fileUri: String) : Int? {
        return withContext(Dispatchers.IO){
            photoDAO.getPhotoIdByFilenameAndUri(filename,fileUri)
        }
    }
}