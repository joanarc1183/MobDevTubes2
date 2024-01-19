package com.example.tubes2

import java.util.Properties

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
