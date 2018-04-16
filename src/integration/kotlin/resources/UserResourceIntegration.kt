package resources

import com.google.gson.Gson
import io.restassured.RestAssured.given
import org.junit.Test
import serializers.inputs.RegistrationSerializer

class UserResourceIntegration : BaseResourceIntegration() {
    @Test
    fun testCreateUser() {
        val data = Gson().toJson(RegistrationSerializer(
            "sjors",
            "sjors",
            "test@test.com"
        ))

        given().body(data)
            .`when`()
            .post("/users")
            .then()
            .statusCode(200)
    }
}
