package service

import io.restassured.RestAssured.get
import io.restassured.RestAssured.post
import org.junit.Test
import kotlin.test.assertEquals

class HelloWorldIntegration : BaseServiceIntegration() {

    @Test
    fun test() {
        val things = get("/helloworld")

        print(things.asString())
        assertEquals(things.statusCode, 200)
    }

    @Test
    fun getByScreenName() {
        val response = get("/profiles/john")

        assertEquals(200, response.statusCode)

        // todo: add empty profile
    }

    @Test
    fun getKweetsByScreenName() {
        val response = get("/profiles/john/kweets")
        assertEquals(200, response.statusCode)
        //todo: add empty profile
    }

    @Test
    fun postFollowScreenname() {
        val response = post("/profiles/hank")

        assertEquals(200, response.statusCode)

        //todo add checks for empty profile and preexisting relation
    }
}
