package com.andmal.app1.data

data class Workout(
    val id: String,
    val record: String?,
    val sets: Int, val comments: String?,
    val creationDate: String?,
    val day: String?,
    val month: String?,
    val week: Int?,
    val year: Int?,
    val type: String?,
    val workoutDate: String?
)