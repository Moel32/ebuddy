package com.moel32.ebuddy.helpers

import com.github.kittinunf.fuel.Fuel
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.httpPost
import com.github.kittinunf.result.Result
import com.github.kittinunf.result.map
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


/**
 * A class for performing symptom checks using the Apimedic API.
 */
class SymptomChecker {

    private val baseUrl = "https://sandbox-healthservice.priaid.ch"
    private var apiKey = ""
    private val language = "en-gb"
    private val format = "json"


    /**
     * Performs a symptom check using the Apimedic API.
     *
     * @param symptoms A list of symptom names to check.
     * @param age The age of the patient in years.
     * @param sex The sex of the patient ("male" or "female").
     * @return A [Result] object containing either a [SymptomCheckResult] object or a [FuelError] object.
     */
    fun checkSymptoms(symptoms: List<String>, age: Int?, sex: String?): Result<SymptomCheckResult, FuelError> {
        apiKey = getToken("kamergenie@gmail.com", "W>U%i9N.mtdWH&\$").get()
        val symptomIds = fetchSymptomIds(symptoms)
        val url = "$baseUrl/diagnosis?&language=$language&symptoms=${symptomIds.joinToString(",")}&token=$apiKey"
        val params = mutableListOf<Pair<String, String>>()
        if (age != null) {
            params.add("age" to age.toString())
        }
        if (sex != null) {
            params.add("gender" to sex)
        }
        val (_, _, result) = url
            .httpGet()
            .responseString()
        return result.fold(
            { json ->
                val symptomCheckResult = Json.decodeFromString<SymptomCheckResult>(json)
                Result.success(symptomCheckResult)
            }, { error -> Result.error(error) }
        )
    }

    private fun fetchSymptomIds(symptoms: List<String>): List<Int> {
        val url = "$baseUrl/symptoms?&language=$language&token=$apiKey"
        val (_, _, result) = Fuel.get(url).responseObject<List<Symptom>>()
        val (symptomList, error) = result
        if (error != null) {
            throw Exception("Error fetching symptoms: ${error.message}")
        }
        val symptomMap = symptomList?.associateBy { it.name } ?: emptyMap()
        return symptoms.mapNotNull { symptomMap[it]?.id }
    }

    /**
     * Retrieves an authentication token from the Apimedic API using a username and password.
     *
     * @param username The username for authentication.
     * @param password The password for authentication.
     * @return A [Result] object containing either a [String] token or a [FuelError] object.
     */
    private fun getToken(username: String, password: String): Result<String, FuelError> {
            val url = "$baseUrl/login"
            val body = listOf(
                "username" to username,
                "password" to password,
                "grant_type" to "client_credentials",
                "client_id" to "symptom-checker",
                "client_secret" to "symptom-checker-secret"
            )
            val (_, _, result) = url.httpPost(body).responseString()
            return result.map { json ->
                val tokenResponse = Json.decodeFromString<TokenResponse>(json)
                tokenResponse.accessToken
            }
        }

    private data class Symptom(val id: Int, val name: String)

    data class SymptomCheckResult(val issues: List<Issue>)

    data class Issue(val name: String, val accuracy: Float, val ranking: Int)


    /**
     * A class representing the JSON response from the Apimedic API when retrieving an authentication token.
     *
     * @property accessToken The access token to be used for API requests.
     * @property tokenType The type of token (e.g. "Bearer").
     * @property expiresIn The number of seconds until the token expires.
     * @property refreshToken A refresh token that can be used to obtain a new access token.
     */
    @Serializable
    data class TokenResponse(
        val accessToken: String,
        val tokenType: String,
        val expiresIn: Int,
        val refreshToken: String
    )
}