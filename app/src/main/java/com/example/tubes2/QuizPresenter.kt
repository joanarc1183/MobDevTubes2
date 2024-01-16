package com.example.tubes2

interface QuizContract {
    interface View {
        fun showQuestion(question: String)
        fun showScore(score: Int)
    }

    interface Presenter {
        fun startQuiz(theme: String)
        fun answerQuestion(isTrue: Boolean)
    }
}

class QuizPresenter(private val view: QuizContract.View) : QuizContract.Presenter {
    private val questions = mutableListOf<QuizQuestion>()
    private var currentQuestionIndex = 0
    private var score = 0

    override fun startQuiz(theme: String) {
        // Implement logic to fetch questions from https://www.swapi.tech/ based on the selected theme
        // For simplicity, hardcoded questions are used in this example
        questions.clear()
        questions.add(QuizQuestion("Is it true?", true))
        questions.add(QuizQuestion("Is it false?", false))
        questions.add(QuizQuestion("Another true or false?", true))

        showCurrentQuestion()
    }

    private fun showCurrentQuestion() {
        if (currentQuestionIndex < questions.size) {
            view.showQuestion(questions[currentQuestionIndex].question)
        } else {
            view.showScore(score)
        }
    }

    override fun answerQuestion(isTrue: Boolean) {
        if (currentQuestionIndex < questions.size) {
            val correctAnswer = questions[currentQuestionIndex].answer
            if (isTrue == correctAnswer) {
                score++
            }
            currentQuestionIndex++
            showCurrentQuestion()
        }
    }
}