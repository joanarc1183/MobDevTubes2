package com.example.tubes2


interface QuizModel {
    fun updateScore(score: Int)
    fun getScore(): Int
    fun calculateScore(): Double
    fun updateTheme(theme: String)
    fun getTheme(): String
}

class Quiz : QuizModel {
    private var score: Int = 0
    private var theme: String = ""
    override fun updateScore(newScore: Int) {
        score = newScore
    }
    override fun getScore(): Int {
        return score
    }
    override fun calculateScore(): Double {
        return (score*1.0/3*100)
    }
    override fun updateTheme(newtheme: String) {
        theme = newtheme
    }
    override fun getTheme(): String {
        return theme
    }
}