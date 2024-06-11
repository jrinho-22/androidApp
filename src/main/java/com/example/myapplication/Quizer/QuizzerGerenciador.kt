package com.example.myapplication.Quizer

data class QuizzerGerenciador(val Quizzer1: Quizzer, val Quizzer2: Quizzer, val Quizzer3: Quizzer, val Quizzer4: Quizzer){
    var respostasCertas: Int = 0

    fun getQuizzerGerenciadorList(): List<Quizzer> {
        return listOf(Quizzer1, Quizzer2, Quizzer3, Quizzer4).shuffled()
    }

    fun verificarResposta(quizzer: Quizzer, resposta: String): Boolean {
        val respostaCorreta : Boolean = quizzer.respostas.respostaCerta.equals(resposta)
        if (respostaCorreta) {
            respostasCertas++
        }
        return respostaCorreta
    }
}