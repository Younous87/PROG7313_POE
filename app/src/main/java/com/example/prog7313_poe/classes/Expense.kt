package com.example.prog7313_poe.classes
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.Date
import kotlin.math.exp
@Entity(
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["categoryID"],
        childColumns = ["categoryID"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = Photo::class,
        parentColumns = ["photoID"],
        childColumns = ["photoID"],
        onDelete = ForeignKey.CASCADE
    ),
    ForeignKey(
        entity = User::class,
        parentColumns = ["userID"],
        childColumns = ["userID"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    val expenseID : Int,
    val time : Time? = null,
    val date : Date? = null,
    val categoryID : String? = null,
    val description : String? = null,
    val amount : Double? = null,
    val photoID : String? = null,
    val transactionType: String? = null,
    val userID : String? = null
){

    //---------------------------------------------------------------------------------------------------------------------------------------//
    // Create an Expense and insert data into db
    //---------------------------------------------------------------------------------------------------------------------------------------//
//    fun createExpense(id: String,date: String, times: String, category_id: String, descript: String, value: String, photo: String, user: String, type: String) :Boolean {
//        val expense = Expense(id, times, category_id, descript, value, photo, user, type)
//        return true
//
//    }
}
