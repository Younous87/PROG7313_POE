package com.example.prog7313_poe.data_access_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
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

}