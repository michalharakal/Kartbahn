package org.kartbahn.common.api

import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.kartbahn.api.KartbahnApi
import kotlin.test.assertTrue

class KartbahnApiTest {
    @Test
    fun testRoads() {
        val api = KartbahnApi()
        runBlocking {
            val roads = api.getRoads()
            assertTrue { roads.roads!!.isNotEmpty() }
        }
    }
}
