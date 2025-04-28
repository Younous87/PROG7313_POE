package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity

class Photo(): AppCompatActivity(){
    lateinit var photoID : String
    lateinit var file : String

    constructor(id: String, name: String, data: String) : this() {
        photoID = id
        file = data
    }
}