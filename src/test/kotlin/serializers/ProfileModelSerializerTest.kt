package serializers

import models.KweetModel
import models.ProfileModel
import org.junit.Before
import org.junit.Test
import util.now
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ProfileModelSerializerTest {
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

        profile.follows.add(profile)
    }

    @Test
    fun testSimpleFacade() {
        val facade = SimpleProfileSerializer(profile)

        assertEquals(facade.screenname, profile.screenname)

        facade.screenname = "HAHA"

        assertNotEquals(facade.screenname, "HAHA")
    }

    @Test
    fun testFacade() {
        val facade = ProfileSerializer(profile)

        assertTrue { facade.kweets.count() == 1 }
        assertTrue { facade.likedTweets.count() == 0 }
        assertEquals(facade.follows, listOf("john"))
        assertNotNull(profile.created)
    }
}
