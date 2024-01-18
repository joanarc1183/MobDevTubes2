package com.example.tubes2

//interface QuizModel {
//    fun updateScore(score: Int)
//    fun getScore(): Int
//    fun getTheme(): String
//}
//
//class QuizScoreModel : QuizModel {
//    private var score: Int = 0
//    private var theme: String = ""
//
//    override fun updateScore(newScore: Int) {
//        score = newScore
//    }
//
//    override fun getScore(): Int {
//        return score
//    }
//
//    override fun getTheme(): String {
//        return theme
//    }
//}
//
data class QuizScoreModel(var score: Int = 0, var theme: String = "")
