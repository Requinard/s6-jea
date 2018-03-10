package dto

import adminGrou
import domain.KwetterUser
import johnProfile
import org.junit.Before
import org.junit.Test
import regularGroup
import util.sha256
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UserFacadeTest {
    val profile = johnProfile

    val user = KwetterUser(
        username = "john",
        password = sha256("john")
    )

    @Before
    fun setup() {
        user.profile = profile
        user.groups.add(regularGroup)
    }

    @Test
    fun testFacade() {
        val facade = UserFacade(user)

        assertEquals(facade.groups, listOf("regular"))

        user.groups.add(adminGrou)

        assertEquals(facade.groups, listOf("regular", "admin"))
        assertNotNull(facade.profile.screenname)
    }
}
