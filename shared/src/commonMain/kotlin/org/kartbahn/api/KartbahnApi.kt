package org.kartbahn.api

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import kotlinx.serialization.json.Json
import org.karbahn.api.models.Roads

class KartbahnApi(
    engine: HttpClientEngine? = null,
    private val endpoint: String = "https://verkehr.autobahn.de/o/") {

    private val client = engine?.let {
        HttpClient(engine) {
            config()
        }
    } ?: HttpClient {
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
        client.get("$endpoint/autobahn")
}