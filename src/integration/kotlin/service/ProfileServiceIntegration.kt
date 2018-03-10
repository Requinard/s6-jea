package service

import io.restassured.RestAssured.get
import org.junit.Test
import kotlin.test.assertEquals

class ProfileServiceIntegration : BaseServiceIntegration() {
    @Test
    fun getBasic() {
        val response = get("/profiles")

        assertEquals(200, response.statusCode)
    }
}
