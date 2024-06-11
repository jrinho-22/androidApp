package com.example.myapplication.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "leaderboard")
data class Leaderboard(

@PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val usernName: String,
    val score: Int

)
{
}

data class LeaderboardResponse(
    val results: List<Leaderboard>
)
