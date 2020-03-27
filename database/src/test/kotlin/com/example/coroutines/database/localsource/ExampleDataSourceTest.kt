package com.example.coroutines.database.localsource

import com.example.coroutines.database.TestDb
import com.example.coroutines.database.test
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.sqlite.driver.JdbcSqliteDriver
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleDataSourceTest {

    private lateinit var driver: SqlDriver
    private lateinit var database: TestDb
    private lateinit var trackLocalSource: ExampleDataSource

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setup() {
        driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY).apply {
            TestDb.Schema.create(this)
        }

        database = TestDb(driver)

        trackLocalSource = ExampleDataSource(
            trackQueries = database.trackQueries,
            pointQueries = database.pointQueries,
            dispatcher = testCoroutineDispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        driver.close()
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `receives null when there is no live track`() = runBlocking {
        trackLocalSource.getLivaTrack()
            .test {
                assertEquals(null, expectItem())

                cancel()
            }
    }

    @Test
    fun `receives update with track when inserted`() = runBlocking {
        trackLocalSource.getLivaTrack()
            .test {
                assertEquals(null, expectItem())

                val track = createTrack(1)
                trackLocalSource.createTrack(track.startedAt)
                assertEquals(track, expectItem())

                cancel()
            }
    }

    private fun createTrack(identifier: Long): Track {
        return Track(
            id = identifier,
            startedAt = identifier,
            points = emptyList()
        )
    }
}
