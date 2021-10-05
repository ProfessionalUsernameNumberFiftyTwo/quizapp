package com.example.quizapp

class Quiz(var questions: MutableList<Question>) {
    var score = 0
    var questionNumber = 0

    fun checkAnswer(answer: String) {
        // if answer is correct, increase score, if not, decrease score

    }

    fun giveQuestion(): Question {
        questionNumber = (Math.random()*questions.size).toInt()
        return questions.removeAt(questionNumber)
    }

    fun areQuestionsRemaining(): Boolean {
        return questions.isEmpty()
    }
}