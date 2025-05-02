package com.example.prog7313_poe.data_access_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.prog7313_poe.classes.CategorySpending
import com.example.prog7313_poe.classes.Expense
import com.example.prog7313_poe.classes.ExpenseWithPhoto

@Dao
interface TransactionDAO {
    @Insert
    suspend fun insertTransaction(transaction: Expense) : Long

    @Delete
    suspend fun deleteTransaction(transaction: Expense)

    @Update
    suspend fun editTransaction(transaction: Expense)

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //Query to fetch total amount spent on each category during a user-selectable period
    //---------------------------------------------------------------------------------------------------------------------------------------//
    @Query(
        """
            Select
                c.categoryName,
                Sum(a.amount) AS totalAmount
            From 
                Expense a
            Inner Join 
                Category c on a.categoryID = c.categoryID
            Where 
                a.userID = :userID
            And 
                a.date Between :startDate And :endDate
            Group By 
                c.categoryName
        """
    )
    suspend fun getTotalSpentByCategoryPerPeriod(
        userID: String,
        startDate: String,
        endDate: String
    ): List<CategorySpending>

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //Query to display a list of all expenses created during a user selectable period
    //---------------------------------------------------------------------------------------------------------------------------------------//
    @Query(
        """
            Select 
                e.*, 
                p.fileUri
            From 
                Expense e
            Left Join 
                Photo p on e.photoID = p.photoID
            Where 
                e.userID = :userID
            And 
                e.date Between :startDate And :endDate
            Order By 
                e.date Desc
    """
    )
    suspend fun getExpensesPerPeriodWithPhoto(
        userID: String,
        startDate: String,
        endDate: String

    ): List<ExpenseWithPhoto>
    //Merge

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //Get the latest income
    //---------------------------------------------------------------------------------------------------------------------------------------//
    @Query(
        """
        SELECT * FROM Expense
        WHERE userID =:userID
        AND transactionType = 'Income'
        AND amount IS NOT NULL
        AND amount != 0
        ORDER BY expenseID DESC
        LIMIT 1
        """
    )
    suspend fun getLatestIncome(userID: String): Expense?

    @Query(
        """
            SELECT * FROM Expense
            WHERE userID = :userID
            AND transactionType = 'Expense'
            AND amount IS NOT NULL
            AND amount != 0
            ORDER BY expenseID DESC
            LIMIT 1
        """
    )
    suspend fun getLatestExpense(userID: String): Expense?

}