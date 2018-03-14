package resources

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class KweetModelServiceIntegration : BaseResourceIntegration() {
    @Test
    fun postMessage() {
        given()
            .post("/kweets/create/hello world")
            .then()
            .statusCode(200)
            .body("message", equalTo("hello world"))
    }

    @Test
    fun getAll() {
        given()
            .get("/kweets")
            .then()
            .statusCode(200)
            .body("", equalTo(emptyList<String>()))
    }

    @Test
    fun getById() {
        given()
            .get("/kweets/5/")
            .then()
            .statusCode(200)
            .body("message", equalTo("Automated entry message"))

        given()
            .get("/kweets/2150240")
            .then()
            .statusCode(404)
    }

    @Test
    fun likeTweetById() {
        given()
            .post("/kweets/512521")
            .then()
            .statusCode(404)

        given()
            .post("/kweets/5/")
            .then()
            .statusCode(200)
    }

    @Test
    fun deleteTweet() {
        given().delete("/kweets/5/")
            .then()
            .statusCode(200)
        //todo add test for userModel with different levels
    }
}
