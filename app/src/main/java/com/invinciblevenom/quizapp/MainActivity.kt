package com.invinciblevenom.quizapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var totalQuestionsTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var ansA: Button
    private lateinit var ansB: Button
    private lateinit var ansC: Button
    private lateinit var ansD: Button
    private lateinit var submitBtn: Button

    private var score = 0
    private val totalQuestion = QuestionAnswer.question.size
    private var currentQuestionIndex = 0
    private var selectedAnswer = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        totalQuestionsTextView = findViewById(R.id.total_questions)
        questionTextView = findViewById(R.id.question)
        ansA = findViewById(R.id.ans_A)
        ansB = findViewById(R.id.ans_B)
        ansC = findViewById(R.id.ans_C)
        ansD = findViewById(R.id.ans_D)
        submitBtn = findViewById(R.id.submit_btn)

        ansA.setOnClickListener(this)
        ansB.setOnClickListener(this)
        ansC.setOnClickListener(this)
        ansD.setOnClickListener(this)
        submitBtn.setOnClickListener(this)

        totalQuestionsTextView.text = "Total questions : $totalQuestion"

        loadNewQuestion()
    }

    override fun onClick(view: View) {

        ansA.setBackgroundColor(Color.WHITE)
        ansB.setBackgroundColor(Color.WHITE)
        ansC.setBackgroundColor(Color.WHITE)
        ansD.setBackgroundColor(Color.WHITE)

        val clickedButton = view as Button
        if (clickedButton.id == R.id.submit_btn) {
            if (selectedAnswer == QuestionAnswer.correctAnswers[currentQuestionIndex]) {
                score++
            }
            currentQuestionIndex++
            loadNewQuestion()

        } else {
            selectedAnswer = clickedButton.text.toString()
            clickedButton.setBackgroundColor(Color.YELLOW)
        }

    }

    private fun loadNewQuestion() {

        if (currentQuestionIndex == totalQuestion) {
            finishQuiz()
            return
        }

        questionTextView.text = QuestionAnswer.question[currentQuestionIndex]
        ansA.text = QuestionAnswer.choices[currentQuestionIndex][0]
        ansB.text = QuestionAnswer.choices[currentQuestionIndex][1]
        ansC.text = QuestionAnswer.choices[currentQuestionIndex][2]
        ansD.text = QuestionAnswer.choices[currentQuestionIndex][3]
    }

    private fun finishQuiz() {
        val passStatus = if (score > totalQuestion * 0.60) {
            "Passed"
        } else {
            "Failed"
        }

        AlertDialog.Builder(this)
            .setTitle(passStatus)
            .setMessage("Score is $score out of $totalQuestion.")
            .setPositiveButton("Restart") { _, _ -> restartQuiz() }
            .setCancelable(false)
            .show()
    }

    private fun restartQuiz() {
        score = 0
        currentQuestionIndex = 0
        loadNewQuestion()
    }
}
