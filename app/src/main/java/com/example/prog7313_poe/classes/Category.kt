package com.example.prog7313_poe.classes
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userID"],
        childColumns = ["userID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryID : Int,
    val categoryName: String? = null,
    val description: String? = null,
    val userID : String? = null,

){
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create Category and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
//    fun createCategory(name: String, budget: String): Boolean{
//
//        val category = Category("",name,budget)
//        if(category.toString().isNotEmpty()){
//            return true
//        }
//        return false
//
//
//    }

}