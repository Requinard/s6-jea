package resources

import io.restassured.RestAssured
import org.junit.Before

abstract class BaseResourceIntegration {
    val token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrd2V0dGVyLXdpZGxmbHkiLCJncm91cHMiOltdLCJpZCI6Im51bGwiLCJleHAiOjE1MjYyOTY1MjcsImlhdCI6MTUyMzcwNDUyNywidXNlcm5hbWUiOiJiYXJyeSBiYWRwYWsifQ.wPxRsFVybPlE-3PsPXRLLAv8bRXwWOz63wWL0uX2nvE"
    val authHeader = Pair("Header", token)
    @Before
    fun setupRestAssured() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.basePath = "/jea-kwetter-1.0/api"
        RestAssured.port = 8080
    }
}
