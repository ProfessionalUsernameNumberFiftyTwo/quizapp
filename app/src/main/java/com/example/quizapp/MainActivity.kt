package com.example.quizapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {


    lateinit var mainView: View
    lateinit var scoreText: TextView
    lateinit var questionText: TextView
    lateinit var finalScoreText: TextView
    lateinit var answer1: Button
    lateinit var answer2: Button
    lateinit var answer3: Button
    lateinit var answer4: Button
    lateinit var mainUI: Group

    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wireWidgets()

        // reading the json from the raw folder

        // step 1: open the raw resource as an InputStream
        // R.raw.question --> R is the Resources class, raw is folder, question is the file
        val inputStream = resources.openRawResource(R.raw.question)
        // step 2: use a buffered reader on the inputstream to read the text into a string
        val jsonText = inputStream.bufferedReader().use {
            // the last line of the use function is returned
            it.readText()
        }

        Log.d(TAG, "onCreate: $jsonText")

        // step 3: use gson to convert the jsonText into a List<Question>
        // log the list of questions and see if Question objects load correctly
        val gson = Gson()
        val qType = object : TypeToken<List<Question>>() { }.type
        val questions = gson.fromJson<MutableList<Question>>(jsonText, qType)
        Log.d(TAG, "onCreate: \n ${questions.toString()}")

        // create Quiz object using the list of questions you just read
        // do any initial setup of the layout to show the first question
        val quiz = Quiz(questions)

        // any quiz-related actions -- scorekeeping, answer-checking, question number tracking, and checking whether or not there are remaining questions
        // these are all the duties of the Quiz class

        var initialClick = true

        mainView.setBackgroundColor(Color.parseColor("#b900c9"))
        questionText.text = resources.getString(R.string.start_text)
        questionText.setTextColor(Color.parseColor("#262626"))
        scoreText.text = resources.getString(R.string.score, quiz.score)
        scoreText.setTextColor(Color.parseColor("#262626"))
        answer1.text = ""
        answer1.setBackgroundColor(Color.parseColor("#c98300"))
        answer2.text = ""
        answer2.setBackgroundColor(Color.parseColor("#c98300"))
        answer3.text = ""
        answer3.setBackgroundColor(Color.parseColor("#c98300"))
        answer4.text = ""
        answer4.setBackgroundColor(Color.parseColor("#c98300"))
        finalScoreText.visibility = View.GONE

        // MainActivity is in charge of the UI and passing information to and from the Quiz class
        answer1.setOnClickListener {
            if(!initialClick) {
                updateQuiz(quiz, answer1.text.toString())
            }
            else {
                newQuestions(quiz)
                initialClick = false
            }
        }

        answer2.setOnClickListener {
            if(!initialClick) {
                updateQuiz(quiz, answer2.text.toString())
            }
            else {
                newQuestions(quiz)
                initialClick = false
            }
        }

        answer3.setOnClickListener {
            if(!initialClick) {
                updateQuiz(quiz, answer3.text.toString())
            }
            else {
                newQuestions(quiz)
                initialClick = false
            }
        }

        answer4.setOnClickListener {
            if(!initialClick) {
                updateQuiz(quiz, answer4.text.toString())
            }
            else {
                newQuestions(quiz)
                initialClick = false
            }
        }

    }

    private fun updateQuiz (quiz: Quiz, answer: String) {
        // tell the quiz what was clicked on and let the quiz determine if the answer was correct
        if(quiz.checkAnswer(answer))
        {
            Toast.makeText(this@MainActivity, resources.getString(R.string.correct), Toast.LENGTH_SHORT).show()
        }
        else
        {
            Toast.makeText(this@MainActivity, resources.getString(R.string.incorrect), Toast.LENGTH_SHORT).show()
        }
        // update the score text view based on the current score
        scoreText.text = resources.getString(R.string.score, quiz.score)
        // ask the quiz if there are more question, and if there are...
        // set the question text and button text and button text to the new question and answer choices
        if (quiz.areQuestionsRemaining()) {
            newQuestions(quiz)
        } else {
            // if there aren't any more questions, then hide a bunch of the UI and give the final score
            finalScoreText.text = resources.getString(R.string.final_score, quiz.score)
            finishGame()
        }
    }

    private fun finishGame() {
        mainUI.visibility = View.GONE
        finalScoreText.visibility = View.VISIBLE
    }

    private fun newQuestions(quiz: Quiz) {
        quiz.giveQuestion()
        questionText.text = quiz.currentQuestion.question
        quiz.currentQuestion.answers.shuffle()
        answer1.text = quiz.currentQuestion.answers[0]
        answer2.text = quiz.currentQuestion.answers[1]
        answer3.text = quiz.currentQuestion.answers[2]
        answer4.text = quiz.currentQuestion.answers[3]
    }

    private fun wireWidgets() {
        mainView = findViewById(R.id.layout_main_background)
        scoreText = findViewById(R.id.textView_main_score)
        questionText = findViewById(R.id.textView_main_question)
        finalScoreText = findViewById(R.id.textView_main_finalScore)
        answer1 = findViewById(R.id.button_main_answer1)
        answer2 = findViewById(R.id.button_main_answer2)
        answer3 = findViewById(R.id.button_main_answer3)
        answer4 = findViewById(R.id.button_main_answer4)
        mainUI = findViewById(R.id.group_main_ui)
    }
}