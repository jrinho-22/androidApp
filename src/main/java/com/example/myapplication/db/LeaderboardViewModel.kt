package com.android.myapplication.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.entities.Leaderboard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LeaderboardViewModel(private val leaderboardDao: LeaderboardDao) : ViewModel() {
    val allLeaderboardData: LiveData<List<Leaderboard>> = leaderboardDao.getAllLeaderboard()
     fun insertLeaderbordData(leaderboard: List<Leaderboard>) {
        viewModelScope.launch {
            leaderboardDao.insertLeaderboardData(leaderboard)

        }
    }
    fun clearLeaderboardTable() {
        viewModelScope.launch(Dispatchers.IO) {
            leaderboardDao.clearLeaderboard()
        }
    }
}


