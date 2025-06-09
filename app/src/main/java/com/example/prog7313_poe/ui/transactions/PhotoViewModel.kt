package com.example.prog7313_poe.ui.transactions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.prog7313_poe.classes.Photo
import com.example.prog7313_poe.classes.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class PhotoViewModel (application: Application) : AndroidViewModel(application) {
    private val db = FirebaseFirestore.getInstance()
    private val photoCollection = db.collection("photos")

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text


    suspend fun insertPhoto(photo : Photo): String?{
        return try {
            val newPhotoRef = photoCollection.document()
            photo.photoID = newPhotoRef.id
            newPhotoRef.set(photo).await()
            newPhotoRef.id
        } catch (e: Exception){
            null
        }
    }
    suspend  fun getPhotoIdById(photoID: String): Photo?{
        return try{
            val snapshot = photoCollection.document(photoID).get().await()
            snapshot.toObject(Photo::class.java)
        }catch (e: Exception){
            null
        }

    }
}