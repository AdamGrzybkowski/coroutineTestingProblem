package com.example.coroutines.database.localsource

data class Track(
    val id: Long,
    val startedAt: Long,
    val points: List<Point>
)

data class Point(
    val id: Long,
    val startedAt: Long
)
