package service

import io.restassured.RestAssured.delete
import io.restassured.RestAssured.get
import io.restassured.RestAssured.post
import org.junit.Test
import kotlin.test.assertEquals

class KweetServiceTest : BaseServiceTest() {
    @Test
    fun postMessage() {
        val response = post("/kweets/hello world")

        assertEquals(200, response.statusCode)
    }

    @Test
    fun getAll() {
        val response = get("/kweets")
        assertEquals(200, response.statusCode)
    }

    @Test
    fun getById() {
        val response = get("/kweets/5")
        assertEquals(200, response.statusCode)
    }

    @Test
    fun likeTweetById() {
        // Non existant tweet
        val response = post("/kweets/52105021402")

        assertEquals(200, response.statusCode)

        val response2 = post("/kweets/5")

        assertEquals(200, response2.statusCode)
    }

    @Test
    fun deleteTweet() {
        val response = delete("/kweets/5")
        assertEquals(200, response.statusCode)

        //todo add test for user with different levels
    }
}
