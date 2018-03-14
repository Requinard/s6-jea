package serializers

import models.KweetModel
import models.ProfileModel
import hankProfile
import org.junit.Before
import org.junit.Test
import utils.now
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class KweetModelSerializerTest {
    val profile = ProfileModel(
        created = now(),
        screenname = "john"
    )

    val kweet = KweetModel(
        created = now(),
        message = "Hello world"
    )

    @Before
    fun setup() {
        kweet.profileModel = profile
        profile.kweets.add(kweet)
        kweet.likedBy.add(profile)
    }

    /**
     * Test the complete facade
     */
    @Test
    fun testFacade() {
        val facade = KweetSerializer(kweet)

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
        val facade = SimpleKweetSerializer(kweet)

        assertNotNull(facade.created)
        assertEquals(facade.profile, profile.screenname)
        assertEquals(facade.message, kweet.message)
    }

    /**
     * Assert whether setters do not actually change anything. this is a proof of work for the other methods
     */
    @Test
    fun testSetNotworks() {
        val facade = KweetSerializer(kweet)
        // Assert set
        facade.message = "haha nee"
        facade.profile = SimpleProfileSerializer(hankProfile)
        assertNotEquals(facade.message, "haha nee")
        assertNotEquals(facade.profile, SimpleProfileSerializer(hankProfile))
    }
}
