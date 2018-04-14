package resources

import com.google.gson.Gson
import io.restassured.RestAssured.given
import org.junit.Test
import serializers.inputs.RegisterSerializer

class UserResourceIntegration : BaseResourceIntegration() {
    @Test
    fun testCreateUser() {
        val data = Gson().toJson(RegisterSerializer(
            "sjors",
            "sjors"
        ))

        given().body(data)
            .`when`()
            .post("/users")
            .then()
            .statusCode(200)
    }
}
