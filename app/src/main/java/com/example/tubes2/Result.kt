package com.example.tubes2

data class ResultPeople(
    val height: String,
    val mass: String,
    val hair_color: String,
    val skin_color: String,
    val eye_color: String,
    val birth_year: String,
    val gender: String,
    val created: String,
    val edited: String,
    val name: String,
    val homeworld: String,
    val url: String
)

data class ResultPlanets(
    val diameter: String,
    val rotation_period: String,
    val orbital_period: String,
    val gravity: String,
    val population: String,
    val climate: String,
    val terrain: String,
    val surface_water: String,
    val created: String,
    val edited: String,
    val name: String,
    val url: String
)

data class ResultStarships(
    val model: String,
    val starship_class: String,
    val manufacturer: String,
    val cost_in_credits: String,
    val length: String,
    val crew: String,
    val passengers: String,
    val max_atmosphering_speed: String,
    val hyperdrive_rating: String,
    val MGLT: String,
    val cargo_capacity: String,
    val consumables: String,
    val pilots: List<String>,
    val created: String,
    val edited: String,
    val name: String,
    val url: String
)