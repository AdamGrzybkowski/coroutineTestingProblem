package com.example.coroutines.database.localsource

import com.example.coroutines.database.TrackQueries
import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToOneOrNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
internal class DbTrackLocalSource constructor(
    private val trackQueries: TrackQueries,
    private val dispatcher: CoroutineDispatcher
) {

    fun getLivaTrack(): Flow<Track?> {
        return trackQueries.selectLive().asFlow()
            .mapToOneOrNull()
            .distinctUntilChanged()
            .map { track ->
                track?.let {
                    Track(
                        id = track.id,
                        startedAt = track.started_at,
                        finishedAt = track.finished_at
                    )
                }
            }
            .flowOn(dispatcher)
    }

    suspend fun createTrack(startedAt: Long) = withContext(dispatcher) {
        trackQueries.insert(started_at = startedAt)
    }
}
