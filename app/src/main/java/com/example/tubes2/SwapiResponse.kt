package com.example.tubes2

data class SwapiResponsePeople(
    val result: PropertiesPeople
)

data class SwapiResponsePlanets(
    val result: PropertiesPlanets
)

data class SwapiResponseStarships(
    val result: PropertiesStarships
)

data class SwapiResponseFilms(
    val result: PropertiesFilms
)

data class SwapiResponseLength(
    val total_records: Int
)