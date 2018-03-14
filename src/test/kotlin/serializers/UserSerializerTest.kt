package serializers

import adminGrou
import models.UserModel
import johnProfile
import org.junit.Before
import org.junit.Test
import regularGroup
import util.sha256
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserSerializerTest {
    val profile = johnProfile

    val user = UserModel(
        username = "john",
        password = sha256("john")
    )

    @Before
    fun setup() {
        user.profileModel = profile
        user.groups.add(regularGroup)
    }

    @Test
    fun testFacade() {
        val facade = UserSerializer(user)

        assertEquals(facade.groups, listOf("regular"))

        user.groups.add(adminGrou)

        assertEquals(facade.groups, listOf("regular", "admin"))
        assertNotNull(facade.profile.screenname)
    }
}
