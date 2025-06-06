//package com.example.prog7313_poe.ui.transactions
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.viewModelScope
////import androidx.room.Room
//import com.example.prog7313_poe.DataBase
//import com.example.prog7313_poe.classes.Photo
//import com.example.prog7313_poe.classes.User
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import kotlinx.coroutines.withContext
//
//class PhotoViewModel (application: Application) : AndroidViewModel(application) {
//
//    private val _text = MutableLiveData<String>().apply {
//        value = "This is notifications Fragment"
//    }
//    val text: LiveData<String> = _text
//
//
//    fun insertPhoto(photo : Photo) = viewModelScope.launch {
//        photoDAO.insertPhoto(photo)
//    }
//
//    suspend fun getPhotoIdByFilenameAndUri(filename: String,fileUri: String) : Int? {
//        return withContext(Dispatchers.IO){
//            photoDAO.getPhotoIdByFilenameAndUri(filename,fileUri)
//        }
//    }
//}