package com.example.prog7313_poe.data_access_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prog7313_poe.classes.Photo

@Dao
interface PhotoDAO {
    @Insert
    suspend fun insertPhoto(photo: Photo)

    @Delete
    suspend fun deletePhoto(photo: Photo)

    @Update
    suspend fun editPhoto(photo: Photo)

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //
    //---------------------------------------------------------------------------------------------------------------------------------------//
    @Query("SELECT photoID FROM Photo WHERE filename = :filename AND fileUri = :fileUri")
    suspend fun getPhotoIdByFilenameAndUri(filename: String,fileUri: String) : Int?
}