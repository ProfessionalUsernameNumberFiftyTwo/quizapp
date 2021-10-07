package com.example.quizapp

data class Question(val category: String, val question: String, val answers: MutableList<String>, val correctAnswer: String) {

}