package resources

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.Test

class ProfileServiceIntegration : BaseResourceIntegration() {
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

    @Test
    fun getByScreenName() {
        given()
            .get("/profiles/john")
            .then()
            .statusCode(200)
            .body("screenname", equalTo("john"))

        given()
            .get("/profiles/blablalbalbalablab")
            .then()
            .statusCode(204)
    }

    @Test
    fun getKweetsByScreenName() {
        given()
            .get("/profiles/john/kweets")
            .then()
            .statusCode(200)
    }

    @Test
    fun postFollowScreenname() {
        given().post("/profiles/hank")
            .then()
            .statusCode(200)

        given().post("/profiles/hank")
            .then()
            .statusCode(304)
    }
}
