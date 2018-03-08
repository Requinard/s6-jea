package dto

import domain.Kweet
import domain.Profile
import hankProfile
import org.junit.Before
import org.junit.Test
import util.now
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KweetFacadeTest {
    val profile = Profile(
        created = now(),
        screenname = "john"
    )

    val kweet = Kweet(
        created = now(),
        message = "Hello world"
    )

    @Before
    fun setup() {
        kweet.profile = profile
        profile.kweets += kweet
        kweet.likedBy += profile
    }

    /**
     * Test the complete facade
     */
    @Test
    fun testFacade() {
        val facade = KweetFacade(kweet)

        // Assert get
        assertNotNull(facade.created)
        assertEquals(facade.message, kweet.message)
        assertTrue { facade.profile.screenname == profile.screenname }
        assertTrue { facade.likes.isNotEmpty() }
    }

    /**
     * Test the simple facade
     */
    @Test
    fun testSimpleFacade() {
        val facade = SimpleKweetFacade(kweet)

        assertNotNull(facade.created)
        assertEquals(facade.profile, profile.screenname)
        assertEquals(facade.message, kweet.message)
    }

    /**
     * Assert whether setters do not actually change anything. this is a proof of work for the other methods
     */
    @Test
    fun testSetNotworks() {
        val facade = KweetFacade(kweet)
        // Assert set
        facade.message = "haha nee"
        facade.profile = SimpleProfileFacade(hankProfile)
        assertNotEquals(facade.message, "haha nee")
        assertNotEquals(facade.profile, SimpleProfileFacade(hankProfile))
    }
}
