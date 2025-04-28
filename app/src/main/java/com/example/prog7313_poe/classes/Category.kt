package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity

class Category() : AppCompatActivity(){
    lateinit var categoryID : String
    lateinit var name : String



    constructor(id: String, description: String) : this() {
        categoryID = id
        name = description
    }

}