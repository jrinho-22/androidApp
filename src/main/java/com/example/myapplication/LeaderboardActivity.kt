package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.myapplication.db.AppDatabase
import com.android.myapplication.db.LeaderboardViewModel
import com.android.myapplication.db.LeaderboardViewModelFactory
import com.example.myapplication.composables.BackgroundImageDemo
import com.example.myapplication.composables.ItemList
import com.example.myapplication.entities.Leaderboard
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class LeaderboardActivity : ComponentActivity() {
    private val leaderboardList = mutableListOf<Leaderboard>()
    private lateinit var leaderboardViewModel: LeaderboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val leaderboardDao = AppDatabase.getDatabase(applicationContext).leaderboardDao()
        val viewModelFactory = LeaderboardViewModelFactory(leaderboardDao)
        leaderboardViewModel = ViewModelProvider(this, viewModelFactory).get(LeaderboardViewModel::class.java)
        val leaderList = listOf(
            Leaderboard(usernName = "nome2", score = 22)
        )
        //saveLeaderboardToDatabase(leaderList)
        loadLeaderboardFromDatabase()
        //leaderboardViewModel.clearLeaderboardTable()
        leaderboardViewModel.allLeaderboardData.observe(this) { user ->
            leaderboardList.clear()
            leaderboardList.addAll(user)
        }

        setContent {
            val leaderboardList2 by leaderboardViewModel.allLeaderboardData.observeAsState(emptyList())

            for (item in leaderboardList){
                println("yerrrrrrrrrrr")
                println(item.usernName)
            }
            MyApplicationTheme {
                BackgroundImageDemo(R.drawable.leaderboard)
                ItemList(leaderboardList2)
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun loadLeaderboardFromDatabase() {
        lifecycleScope.launch {
            leaderboardViewModel.allLeaderboardData.observe(this@LeaderboardActivity){ leaderboard ->
                leaderboardList.clear()
                leaderboardList.addAll(leaderboard)
            }
        }
    }

    private fun saveLeaderboardToDatabase(leader: List<Leaderboard>) {
        lifecycleScope.launch {
            leaderboardViewModel.insertLeaderbordData(leader)
        }
        val allLeaderboard = leaderboardViewModel.allLeaderboardData

        allLeaderboard.observe(this@LeaderboardActivity) { leaderList ->
            // userList is the list of users when data is available
            // Print each user
            for (user in leaderList) {
                println("User ID: ${user.id}, Name: ${user.usernName}")
            }
        }
    }
}
