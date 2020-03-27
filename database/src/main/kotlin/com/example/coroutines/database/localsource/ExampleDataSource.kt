package com.example.coroutines.database.localsource

import com.example.coroutines.database.PointQueries
import com.example.coroutines.database.TrackQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExampleDataSource constructor(
    private val trackQueries: TrackQueries,
    private val pointQueries: PointQueries,
    private val dispatcher: CoroutineDispatcher
) {

    fun getLivaTrack(): Flow<Track?> {
        val track = trackQueries.selectAll().asFlow()
            .mapToOneOrNull()
            .distinctUntilChanged()
            .flowOn(dispatcher)
        val points = pointQueries.selectAll().asFlow()
            .mapToList()
            .distinctUntilChanged()
            .flowOn(dispatcher)
        return track.combine(points) { track, points ->
                track?.let {
                    Track(
                        id = track.id,
                        startedAt = track.started_at,
                        points = points.map { Point(it.id, it.started_at) }
                    )
                }
            }
            .flowOn(dispatcher)
    }

    suspend fun createTrack(startedAt: Long) = withContext(dispatcher) {
        trackQueries.insert(started_at = startedAt)
    }
}
