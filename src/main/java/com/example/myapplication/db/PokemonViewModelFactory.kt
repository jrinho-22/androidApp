package com.android.myapplication.db

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LeaderboardViewModelFactory(private val leaderboardDao: LeaderboardDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LeaderboardViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LeaderboardViewModel(leaderboardDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
