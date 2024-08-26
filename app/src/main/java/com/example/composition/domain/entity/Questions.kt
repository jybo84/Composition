package com.example.composition.domain.entity

data class Questions(
    val sum: Int,
    val visibleNumber: Int,
    val options: List<Int>,
)