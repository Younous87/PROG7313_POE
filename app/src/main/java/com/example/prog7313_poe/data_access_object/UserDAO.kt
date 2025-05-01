package com.example.prog7313_poe.data_access_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prog7313_poe.classes.User

@Dao
interface UserDAO {
    @Insert
    suspend fun insertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Update
    suspend fun editUser(user: User)

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //
    //---------------------------------------------------------------------------------------------------------------------------------------//
    @Query(Select * From User Where email = :email And password = :password)
    fun validateLogin(email: String, password: String): User?
}