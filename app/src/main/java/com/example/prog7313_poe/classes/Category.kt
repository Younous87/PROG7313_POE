package com.example.prog7313_poe.classes

data class Category(
    val categoryID : String? = null,
    val description: String? = null,
    val userID : String? = null

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