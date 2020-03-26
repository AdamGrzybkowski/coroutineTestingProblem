package com.example.coroutines.database.localsource

import com.example.coroutines.database.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleDataSourceTest {

    private lateinit var exampleDataSource: ExampleDataSource

    private val testCoroutineDispatcher = TestCoroutineDispatcher()

    @BeforeEach
    fun setup() {
        exampleDataSource = ExampleDataSource(
            dispatcher = testCoroutineDispatcher
        )
    }

    @AfterEach
    fun tearDown() {
        testCoroutineDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `receives null as default`() = runBlocking {
        exampleDataSource.getData()
            .test {
                assertEquals(null to null, expectItem())

                cancel()
            }
    }

    @Test
    fun `receives data1`() = runBlocking {
        exampleDataSource.getData()
            .test {
                assertEquals(null to null, expectItem())

                exampleDataSource.addData1("data1")
                assertEquals("data1" to null, expectItem())

                cancel()
            }
    }

    @Test
    fun `receives data1 and data1data2 combined`() = runBlocking {
        exampleDataSource.getData()
            .test {
                assertEquals(null to null, expectItem())

                exampleDataSource.addData1("data1")
                assertEquals("data1" to null, expectItem())

                exampleDataSource.addData2("data2")
                assertEquals("data1" to "data2", expectItem())

                cancel()
            }
    }
}

