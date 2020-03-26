package com.example.coroutines.database.localsource

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
internal class ExampleDataSource constructor(
    private val dispatcher: CoroutineDispatcher
) {

    private val channel1 = ConflatedBroadcastChannel<String?>(null)

    private val channel2 = ConflatedBroadcastChannel<String?>(null)

    fun getData(): Flow<Pair<String?, String?>> {
        val flow1 = channel1.asFlow()
            .flowOn(dispatcher)
        val flow2 = channel2.asFlow()
            .flowOn(dispatcher)
        return flow1.combine(flow2) { data1, data2 ->
                data1 to data2
            }
            .flowOn(dispatcher)
    }

    suspend fun addData1(value: String?) = withContext(dispatcher) {
        channel1.send(value)
    }

    suspend fun addData2(value: String?) = withContext(dispatcher) {
        channel2.send(value)
    }
}
