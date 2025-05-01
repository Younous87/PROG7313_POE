package com.example.prog7313_poe

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prog7313_poe.classes.Category
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.Expense
import com.example.prog7313_poe.classes.Goal
import com.example.prog7313_poe.classes.Photo
import com.example.prog7313_poe.classes.User
import com.example.prog7313_poe.data_access_object.CategoryDAO
import com.example.prog7313_poe.data_access_object.GoalDAO
import com.example.prog7313_poe.data_access_object.PhotoDAO
import com.example.prog7313_poe.data_access_object.TransactionDAO
import com.example.prog7313_poe.data_access_object.UserDAO

@Database(
    entities = [Category::class, Expense::class, Goal::class, Photo::class, User::class, CategorySpending::class],
    version = 1,
)
abstract class DataBase: RoomDatabase() {
    abstract val cDao: CategoryDAO
    abstract val gDao: GoalDAO
    abstract val pDao: PhotoDAO
    abstract val tDao: TransactionDAO
    abstract val uDao: UserDAO
}