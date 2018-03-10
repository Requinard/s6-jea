package service

import io.restassured.RestAssured.get
import org.junit.Test
import kotlin.test.assertEquals

class ProfileServiceTest : BaseServiceTest() {
    @Test
    fun getBasic() {
        val response = get("/profiles")

        assertEquals(200, response.statusCode)
    }
}
