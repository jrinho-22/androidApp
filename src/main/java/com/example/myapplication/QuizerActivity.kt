package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.android.myapplication.db.AppDatabase
import com.android.myapplication.db.LeaderboardViewModel
import com.android.myapplication.db.LeaderboardViewModelFactory
import com.example.myapplication.composables.BackgroundImageDemo
import com.example.myapplication.composables.Carousel
import com.example.myapplication.composables.GoToLeaderboard
import com.example.myapplication.composables.ItemListQuizer
import com.example.myapplication.Quizer.Quizzer
import com.example.myapplication.Quizer.QuizzerGerenciador
import com.example.myapplication.Quizer.Resposta
import com.example.myapplication.entities.Leaderboard
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class QuizerActivity : ComponentActivity(){
    private var quizzerList: List<Quizzer> = emptyList()
    private lateinit var leaderboardViewModel: LeaderboardViewModel
    private lateinit var quizerGerenciador: QuizzerGerenciador
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val leaderboardDao = AppDatabase.getDatabase(applicationContext).leaderboardDao()
        val viewModelFactory = LeaderboardViewModelFactory(leaderboardDao)
        leaderboardViewModel = ViewModelProvider(this, viewModelFactory).get(LeaderboardViewModel::class.java)
        initializeQuizer()

        setContent {
            // val leaderboardList2 by quizerList.observeAsState(emptyList())
            MyApplicationTheme {
                BackgroundImageDemo(R.drawable.quizer)
                //ItemListQuizer(quizerList)
                Carousel(
                    saveLeaderboardToDatabase = { list -> saveLeaderboardToDatabase(list) },
                    quizerGerenciador = quizerGerenciador,
                    quizzerList = quizzerList,
                )
            }
        }
    }

    private fun initializeQuizer(){
        val respostas1 = Resposta(
            "Propriedades Normais e anormais.",
            "Propriedades Simples e raras.",
            "Propriedades antigas e atuais.",
            "Propriedades Gerais e especificas.")

        val respostas2 = Resposta(
            "Fenômeno Comum.",
            "Fenômeno Sólido.",
            "Fenômeno Liquido.",
            "Fenômeno Químico."
        )

        val respostas3 = Resposta(
            "São 5 componentes e 2 fases..",
            "São 4 componentes e 4 fases.",
            "São 7 fases com 5 componentes.",
            "São 4 componentes e 3 fases.")

        val respostas4 = Resposta(
            "Quando passa do estado liquido para o sólido.",
            "Quando passa do estado gasoso para o liquido.",
            "Quando passa do estado Liquido para o gasoso.",
            "Quando passa do estado solido pro liquido.")

        val respostas5 = Resposta("1", "2", "3", "4")

        quizerGerenciador = QuizzerGerenciador(
            Quizzer("Existem 2 tipos de propriedades da matéria. Quais são elas?", respostas1, R.drawable.chem1),
            Quizzer("O Açúcar sendo aquecido e um exemplo de?", respostas2,  R.drawable.chem2),
            Quizzer("Quantas fases e quantos componentes terá uma mistura contendo água, sal, óleo e areia?", respostas3,  R.drawable.chem3),
            Quizzer("Ocorre a fusão quando:", respostas4,  R.drawable.quem4)
        )
        quizzerList = quizerGerenciador.getQuizzerGerenciadorList()
    }

    private fun saveLeaderboardToDatabase(leader: List<Leaderboard>) {
        lifecycleScope.launch {
            leaderboardViewModel.insertLeaderbordData(leader)
        }
    }
}