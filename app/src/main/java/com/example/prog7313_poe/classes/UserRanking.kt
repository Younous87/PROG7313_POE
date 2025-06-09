package com.example.prog7313_poe.classes

data class UserRanking(
    val userId: String = "",
    val transactionXP: Int = 0,
    val goalXP: Int = 0,
    val dayStreakXP: Int = 0,
    val overallXP: Int = 0,
    val transactionRank: String = "Bronze",
    val goalRank: String = "Bronze",
    val dayStreakRank: String = "Bronze",
    val overallRank: String = "Bronze",
    val lastLoginDate: String = "",
    val currentStreak: Int = 0,
    val totalTransactions: Int = 0,
    val totalGoals: Int = 0
)