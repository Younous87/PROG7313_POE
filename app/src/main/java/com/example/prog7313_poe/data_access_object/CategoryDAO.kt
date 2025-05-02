package com.example.prog7313_poe.data_access_object

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prog7313_poe.classes.Category

@Dao
interface CategoryDAO {
    @Insert
    suspend fun insertCategory(category: Category)

    @Delete
    suspend fun deleteCategory(category: Category)

    @Update
    suspend fun editCategory(category: Category)

    //Test Query
    @Query("SELECT* FROM Category")
     fun getAllCategories() : LiveData<List<Category>>

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //
    //---------------------------------------------------------------------------------------------------------------------------------------//

}