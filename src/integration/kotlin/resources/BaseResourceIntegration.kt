package resources

import io.restassured.RestAssured
import io.restassured.RestAssured.basic
import org.junit.Before

abstract class BaseResourceIntegration {
    @Before
    fun setupRestAssured() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.basePath = "/jea-kwetter-1.0-SNAPSHOT/api"
        RestAssured.port = 8080
        RestAssured.authentication = basic("steve", "steve")
    }
}
