package com.example.prog7313_poe.classes

data class Category(
    val categoryID : String? = null,
    val description: String? = null,
    val userID : String? = null

){
    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create Category and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
    fun createCategory(catdescription: String, userid :String): Boolean{

        val category = Category("",catdescription,userid)
        return true
    }

}