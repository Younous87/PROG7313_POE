package com.example.prog7313_poe.data_access_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.Expense

@Dao
interface TransactionDAO {
    @Insert
    suspend fun insertTransaction(transaction: Expense)

    @Delete
    suspend fun deleteTransaction(transaction: Expense)

    @Update
    suspend fun editTransaction(transaction: Expense)

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //
    //---------------------------------------------------------------------------------------------------------------------------------------//
    @Query(
        """
            Select
                c.categoryName,
                Sum(a.amount) AS totalAmount
            From
            Inner Join Category c on a.categoryID = c.categoryID
            Where a.userID = :userID
            And a.date Between :startDate And :endDate
            Group By c.categoryName
        """
    )
    suspend fun getTotalSpentByCategoryPerPeriod(
        userID: String,
        startDate: String,
        endDate: String
    ): List<CategorySpending>
}