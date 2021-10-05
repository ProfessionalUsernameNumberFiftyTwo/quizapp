package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"
    lateinit var mainView: View
    lateinit var scoreText: TextView
    lateinit var questionText: TextView
    lateinit var answer1: Button
    lateinit var answer2: Button
    lateinit var answer3: Button
    lateinit var answer4: Button

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
        var quiz = Quiz(questions)

        // any quiz-related actions -- scorekeeping, answer-checking, question number tracking, and checking whether or not there are remaining questions
        // these are all the duties of the Quiz class

        // MainActivity is in charge of the UI and passing information to and from the Quiz class
        answer1.setOnClickListener {
            // tell the quiz what was clicked on and let the quiz determine if the answer was correct

            // update the score text view based on the current score

            // ask the quiz if there are more question, and if there are...
            // set the question text and button text and button text to the new question and answer choices

            // if there aren't any more questions, then hide a bunch of the UI and give the final score
        }

    }

    private fun wireWidgets() {
        mainView = findViewById(R.id.layout_main_background)
        scoreText = findViewById(R.id.textView_main_score)
        questionText = findViewById(R.id.textView_main_question)
        answer1 = findViewById(R.id.button_main_answer1)
        answer2 = findViewById(R.id.button_main_answer2)
        answer3 = findViewById(R.id.button_main_answer3)
        answer4 = findViewById(R.id.button_main_answer4)
    }
}