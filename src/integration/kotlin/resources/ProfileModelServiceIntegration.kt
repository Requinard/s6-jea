package resources

import io.restassured.RestAssured.given
import org.hamcrest.Matchers.equalTo
import org.junit.Test
import serializers.inputs.ChangeProfileSerializer

class ProfileModelServiceIntegration : BaseResourceIntegration() {
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
        val firstProfileSerializer = ChangeProfileSerializer(
            "haha ja",
            "http://google.com",
            "Fontys"
        )

        // Run
        given().body(firstProfileSerializer)
            .header("Authorization", token)
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
