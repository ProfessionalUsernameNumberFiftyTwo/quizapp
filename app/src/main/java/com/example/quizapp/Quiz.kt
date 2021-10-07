package com.example.quizapp

class Quiz(var questions: MutableList<Question>) {
    var score = 0
    var questionNumber = 0
    lateinit var currentQuestion: Question

    fun checkAnswer(answer: String): Int {
        // if answer is correct, increase score, if not, decrease score
        if (answer.equals(currentQuestion.correctAnswer)) {
            score++
            return score
        }
        else {
            score--
            return score
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