package resources

import io.restassured.RestAssured.get
import org.junit.Test
import kotlin.test.assertEquals

class HelloWorldIntegration : BaseResourceIntegration() {

    @Test
    fun test() {
        val things = get("/helloworld")

        print(things.asString())
        assertEquals(things.statusCode, 200)
    }
}
