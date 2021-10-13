package com.example.quizapp

import android.widget.Toast

class Quiz(var questions: MutableList<Question>) {
    var score = 0
    var questionNumber = 0
    lateinit var currentQuestion: Question

    fun checkAnswer(answer: String): Boolean {
        // if answer is correct, increase score, if not, decrease score
        if (answer.equals(currentQuestion.correctAnswer)) {
            score++
            return true
        }
        else {
            score--
            return false
        }
    }

    fun giveQuestion(): Question {
        questionNumber = (Math.random()*questions.size).toInt()
        currentQuestion = questions[questionNumber]
        return questions.removeAt(questionNumber)
    }

    fun areQuestionsRemaining(): Boolean {
        return questions.isNotEmpty()
    }
}