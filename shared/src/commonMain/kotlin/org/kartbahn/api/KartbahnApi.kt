package org.kartbahn.api

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import org.kartbahn.api.models.ElectricChargingStations
import org.kartbahn.api.models.Roads
import org.kartbahn.api.models.Roadworks
import org.kartbahn.api.models.Warnings

class KartbahnApi(
    private val endpoint: String = "https://verkehr.autobahn.de/o/autobahn"
) {

    private val client = HttpClient {
        config()
    }

    private fun HttpClientConfig<*>.config() {
        install(JsonFeature) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
            serializer = KotlinxSerializer(Json { isLenient = true; ignoreUnknownKeys = true })
        }
    }

    // https://verkehr.autobahn.de/o/autobahn/
    suspend fun getRoads(): Roads =
        client.get(endpoint)

    // /{roadId}/services/roadworks
    suspend fun getRoadWorks(roadId: String): Roadworks =
        client.get("$endpoint/${roadId}/services/roadworks")

    // https://verkehr.autobahn.de/o/autobahn/A1/services/warning
    suspend fun getWarnings(roadId: String): Warnings =
        client.get("$endpoint/${roadId}/services/warning")

    // https://verkehr.autobahn.de/o/autobahn/A1/services/electric_charging_station
    suspend fun getElectricChargingStations(roadId: String): ElectricChargingStations =
        client.get("$endpoint/${roadId}/services/electric_charging_station")
}