package com.example.fithub.models

data class Exercise (
    val id: Long,
    val name: String,
    val muscleGroup: MuscleGroup,
    val description: String
)