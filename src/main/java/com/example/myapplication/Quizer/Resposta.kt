package com.example.myapplication.Quizer


data class Resposta(
    val resposta1: String,
    val resposta2: String,
    val resposta3: String,
    val respostaCerta: String
){
    fun getAllRespostas(): List<String> {
        return listOf(resposta1, resposta2, resposta3, respostaCerta).shuffled()
    }

    fun escolherRespostas(reposta: String): Boolean {
        return respostaCerta.equals(reposta)
    }
}