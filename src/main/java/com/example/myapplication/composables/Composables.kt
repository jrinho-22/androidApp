package com.example.myapplication.composables

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.material.Card
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.myapplication.LeaderboardActivity
import com.example.myapplication.MainActivity
import com.example.myapplication.QuizerActivity
import com.example.myapplication.R
import com.example.myapplication.Quizer.Quizzer
import com.example.myapplication.Quizer.QuizzerGerenciador
import com.example.myapplication.Quizer.Resposta
import com.example.myapplication.entities.Leaderboard
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlin.math.abs


@Composable
fun GoToLeaderboard(){
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            context.startActivity(Intent(context, LeaderboardActivity::class.java))
        }) {
            Text(
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                text = "Leaderboard"
            )
        }
        Button(onClick = {
            context.startActivity(Intent(context, QuizerActivity::class.java))
        }) {
            Text(
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                text = "Quizer")
        }
    }
}

data class ListItem(val id: Int, val text: String)

// Composable function to display a list of items
@Composable
fun ItemList(items: List<Leaderboard>) {
    for (item in items){
        println("yerrrrrrrrrrr")
        println(item.usernName)
    }
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 90.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                context.startActivity(Intent(context, MainActivity::class.java))
            }) {
            Text(
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp),
                text = "Menu"
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = 8.dp,
            backgroundColor = Color.White,
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                items.forEach { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 0.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text(
                            text = "NOME: " + item.usernName + "  |  PONTUAÇÃO: " + item.score,
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.CenterVertically)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Text(
                            text = "",
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 3.sp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Carousel(
    saveLeaderboardToDatabase: (List<Leaderboard>) -> Unit,
    quizerGerenciador: QuizzerGerenciador,
    quizzerList: List<Quizzer>,
) {
    val keys: List<String> = listOf("A", "B", "C", "D")
    var text by remember { mutableStateOf(TextFieldValue("")) }
    var isError by remember { mutableStateOf<Boolean?>(null) }
    val context = LocalContext.current
    var respostas1: MutableList<String> = quizzerList[0].respostas.getAllRespostas().toMutableList()
    //respostas1.add("1")
    var respostas2: MutableList<String> = quizzerList[1].respostas.getAllRespostas().toMutableList()
    //respostas2.add("2")
    var respostas3: MutableList<String> = quizzerList[2].respostas.getAllRespostas().toMutableList()
    //respostas3.add("3")
    var respostas4: MutableList<String> = quizzerList[3].respostas.getAllRespostas().toMutableList()
    //respostas4.add("4")

    val state = remember { mutableStateListOf<Boolean?>(null, null, null, null) }

    fun keyValuePairs(keys: List<String>, values: List<String>): List<String> {
        return keys.zip(values).map { "${it.first}:${it.second}" }
    }
    respostas1 = keyValuePairs(keys, respostas1).toMutableList()
    respostas1.add("1")
    respostas2 = keyValuePairs(keys, respostas2).toMutableList()
    respostas2.add("2")
    respostas3 = keyValuePairs(keys, respostas3).toMutableList()
    respostas3.add("3")
    respostas4 = keyValuePairs(keys, respostas4).toMutableList()
    respostas4.add("4")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxHeight()
            .padding(top = 170.dp)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        TextField(
            value = text,
            onValueChange = { newText ->
                text = newText
                isError = text.text.isBlank() // Simple error check
            },
            singleLine = true,
            label = { Text("Enter your name") },
            isError = isError ?: false,
            shape = RoundedCornerShape(8.dp),
        )
        if (isError == true) {
            Text("This field cannot be empty", color = Color.Red)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            enabled = when {
                isError == false -> true
                isError == true -> false
                else -> false
            },
            onClick = {
                val leaderList = listOf(
                    Leaderboard(usernName = text.text, score = quizerGerenciador.respostasCertas),
                )
                saveLeaderboardToDatabase(leaderList)
                context.startActivity(Intent(context, LeaderboardActivity::class.java))
            }) {
            Text(text = "Finalizar Quiz")
        }
    }

    BoxWithConstraints(
        modifier = Modifier.padding(top = 310.dp)
    ) {
        val halfRowWidth = constraints.maxWidth / 2
        val itemCount = quizzerList.size
        val listState = rememberLazyListState()

        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            items(count = Int.MAX_VALUE) { index ->
                val currentIndex = index % itemCount // Calculate the current index
                val respostasList = when (currentIndex) {
                    0 -> respostas1
                    1 -> respostas2
                    2 -> respostas3
                    3 -> respostas4
                    else -> respostas1 // Handle additional cases if needed
                }

                ItemListQuizer(
                    preFix = respostasList[respostasList.lastIndex],
                    gerenciador = quizerGerenciador,
                    item = quizzerList[currentIndex],
                    respostasList = respostasList,
                    state = state[currentIndex],
                    index = currentIndex
                ) { newState, index ->
                    state[index] = newState
                }
            }
        }
        //val context = LocalContext.current
    }
}


@Composable
fun ItemListQuizer(preFix: String, gerenciador: QuizzerGerenciador, item: Quizzer, respostasList: List<String>, state: Boolean?, index: Int, onStateChange: (Boolean?, Int) -> Unit) {
    val itemm by remember { mutableStateOf(item) } // Store shuffled list
    val backgroundColor by animateColorAsState(
        targetValue = when {
            state == false -> Color.Red
            state == true -> Color.Green
            else -> Color.Transparent
        },
        animationSpec = tween(
            durationMillis = 1500, // Change the duration to make the animation slower
            easing = FastOutSlowInEasing
        )
    )
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = 8.dp,
        backgroundColor = Color.White,
        //shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = backgroundColor
                    )
                    .padding(vertical = 1.dp),
                horizontalArrangement = Arrangement.Start,
            ) {
                Text(
                    text = preFix + ")  " + itemm.pergunta,
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )
            }
            Row (
                modifier = Modifier.padding(vertical = 7.dp)
            ) {
                Image(
                    painter = painterResource(itemm.imagem),
                    contentDescription = null, // Provide a content description if needed
                    modifier = Modifier.fillMaxWidth().size(150.dp)// Adjust size as needed
                )
            }
            respostasList.dropLast(1).forEach { respostaa ->
                val respostaPair: List<String> = respostaa.split(":")
                val resposta: String = respostaPair[1]
                val preFix: String = respostaPair[0]
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start,

                ) {
                    Button(onClick = {
                        val newState = gerenciador.verificarResposta(itemm, resposta)
                        //state = newState
                        onStateChange(newState, index)
                    },
                        shape = RoundedCornerShape(0.dp),
                        enabled = when {
                            state == false -> false
                            state == true -> false
                            else -> true
                        },
                        colors = ButtonDefaults.textButtonColors(Color.Transparent),
                        contentPadding = PaddingValues(),
                    ) {
                        Text(
                            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                            text = preFix + ")  " + resposta
                        )
                    }
                }
            }
        }
    }
}

/*@Composable
fun ItemListQuizer(items: List<Quizzer>) {
    for (item in items){
        println("yerrrrrrrrrrr")
        println(item.pergunta)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 300.dp)
            .padding(16.dp),
        elevation = 8.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            items.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center

                ) {
                    Text(
                        text = item.pergunta,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}*/


@Composable
fun BackgroundImageDemo(image: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = image),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}