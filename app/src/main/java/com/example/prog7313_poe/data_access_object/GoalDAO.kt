package com.example.prog7313_poe.data_access_object

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.prog7313_poe.classes.Goal

@Dao
interface GoalDAO {
    @Insert
    suspend fun insertBudgetGoal(goal: Goal)

    @Delete
    suspend fun deleteBudgetGoal(goal: Goal)

    @Update
    suspend fun editBudgetGoal(goal: Goal)

    //---------------------------------------------------------------------------------------------------------------------------------------//
    //
    //---------------------------------------------------------------------------------------------------------------------------------------//

}