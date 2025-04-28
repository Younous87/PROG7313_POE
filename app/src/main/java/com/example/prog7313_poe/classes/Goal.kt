package com.example.prog7313_poe.classes

import androidx.appcompat.app.AppCompatActivity

class Goal(): AppCompatActivity(){
    lateinit var goal_ID : String
    lateinit var userID : String
    lateinit var month : String
    lateinit var minimum : String
    lateinit var maximum : String

    constructor(id: String,mon: String, id2: String, max: String, min: String) : this() {
        goal_ID = id
        userID = id2
        month = mon
        maximum = max
        minimum = min
    }
}