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
    private var apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImthbWVyZ2VuaWVAZ21haWwuY29tIiwicm9sZSI6IlVzZXIiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9zaWQiOiIxMjQyNyIsImh0dHA6Ly9zY2hlbWFzLm1pY3Jvc29mdC5jb20vd3MvMjAwOC8wNi9pZGVudGl0eS9jbGFpbXMvdmVyc2lvbiI6IjIwMCIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbGltaXQiOiI5OTk5OTk5OTkiLCJodHRwOi8vZXhhbXBsZS5vcmcvY2xhaW1zL21lbWJlcnNoaXAiOiJQcmVtaXVtIiwiaHR0cDovL2V4YW1wbGUub3JnL2NsYWltcy9sYW5ndWFnZSI6ImVuLWdiIiwiaHR0cDovL3NjaGVtYXMubWljcm9zb2Z0LmNvbS93cy8yMDA4LzA2L2lkZW50aXR5L2NsYWltcy9leHBpcmF0aW9uIjoiMjA5OS0xMi0zMSIsImh0dHA6Ly9leGFtcGxlLm9yZy9jbGFpbXMvbWVtYmVyc2hpcHN0YXJ0IjoiMjAyMy0wNi0wNCIsImlzcyI6Imh0dHBzOi8vc2FuZGJveC1hdXRoc2VydmljZS5wcmlhaWQuY2giLCJhdWQiOiJodHRwczovL2hlYWx0aHNlcnZpY2UucHJpYWlkLmNoIiwiZXhwIjoxNjg1OTE4ODIwLCJuYmYiOjE2ODU5MTE2MjB9.zwQjbApj6wfcxEquHlWlZ6gA9AvbP54DAFtkvVaLZgY"
    private val language = "en-gb"
    private val format = "json"


    /**
     * Performs a symptom check using the Apimedic API.
     *
     * @param symptomIds A list of symptom ids to check.
     * @param age The age of the patient in years.
     * @param sex The sex of the patient ("male" or "female").
     * @return A [Result] object containing either a [SymptomCheckResult] object or a [FuelError] object.
     */
    fun checkSymptoms(symptomIds: List<Int>, year_of_birth: Int?, gender: String?): Result<SymptomCheckResult, FuelError> {
        val url = "$baseUrl/diagnosis?language=$language&gender=$gender&year_of_birth=$year_of_birth&symptoms=[${symptomIds.joinToString(",")}]&token=$apiKey"
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