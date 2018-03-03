package service

import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


class HelloWorldServiceTest {
    lateinit var helloWorldService: HelloWorldService

    @Before
    fun setup(){
        helloWorldService = HelloWorldService()
    }

    @Test
    fun basicTest(){
        assertTrue { 1 == 1 }
    }

    @Test
    fun helloWorldTest(){
        assertEquals("Hello World", helloWorldService.get())
    }
}
