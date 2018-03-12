package service

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class ProfileServiceIntegration : BaseServiceIntegration() {
    @Test
    fun getBasic() {
        given()
            .`when`()
            .get("/profiles")
            .then()
            .statusCode(200)
            .body("screenname", equalTo("john"))
    }

    @Test
    fun putNewProfile() {
        //Assert is empty
        given()
            .`when`()
            .get("/profiles")
            .then()
            .body("bio", equalTo(""))
            .body("website", equalTo("www.kwetter.nl"))
            .body("location", equalTo("Kwetter!"))

        // Run
        given()
            .param("bio", "haha ja")
            .param("website", "http://google.com")
            .param("location", "fontys")
            .`when`()
            .put("/profiles")
            .then()
            .statusCode(200)
            .body("bio", equalTo("haha ja"))
            .body("website", equalTo("http://google.com"))
            .body("location", equalTo("fontys"))
    }
}
