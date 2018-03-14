package resources

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HelloWorldResourceTest {
    lateinit var helloWorldResource: HelloWorldResource

    @Before
    fun setup() {
        helloWorldResource = HelloWorldResource()
    }

    @Test
    fun basicTest() {
        assertTrue { 1 == 1 }
    }

    @Test
    fun helloWorldTest() {
        assertEquals("Hello World", helloWorldResource.get())
    }
}
