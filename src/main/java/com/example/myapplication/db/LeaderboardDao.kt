package com.android.myapplication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.entities.Leaderboard


@Dao
interface LeaderboardDao {

@Insert(onConflict = OnConflictStrategy.REPLACE)
suspend fun insertLeaderboardData(leaderboardData: List<Leaderboard>)

@Query("SELECT * FROM leaderboard ORDER BY score DESC LIMIT 10")
fun getAllLeaderboard(): LiveData<List<Leaderboard>>

@Query("DELETE FROM leaderboard")
suspend fun clearLeaderboard()
}
