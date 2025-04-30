package com.example.prog7313_poe.classes
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryID : String,
    val categoryName: String? = null,
    val description: String? = null,
    val userID : String? = null,

){
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create Category and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
    fun createCategory(name: String, budget: String): Boolean{

        val category = Category("",name,budget)
        if(category.toString().isNotEmpty()){
            return true
        }
        return false


    }

}