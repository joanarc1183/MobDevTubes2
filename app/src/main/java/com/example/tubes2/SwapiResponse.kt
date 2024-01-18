package com.example.tubes2

//data class SwapiResponse(
//    val message: String,
//    val result: Result,
//    val description: String,
//    val _id: String,
//    val uid: String,
//    val __v: String
//)

data class SwapiResponsePeople(
    val result: PropertiesPeople
)

data class SwapiResponsePlanets(
    val result: PropertiesPlanets
)

data class SwapiResponseStarships(
    val result: PropertiesStarships
)

data class SwapiResponseLength (
    val total_pages: Int,
    val results: String
)
