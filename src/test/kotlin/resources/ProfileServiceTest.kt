package resources

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import johnProfile
import johnUser
import org.mockito.Mockito
import services.ProfileService
import services.UserService
import utils.JwtUtils
import javax.ws.rs.core.HttpHeaders
import kotlin.test.assertEquals

/**
 * Disabled due to responsess
 */
class ProfileServiceTest {
    val profile = johnProfile
    val user = johnUser

    lateinit var profileService: ProfileResource

    fun setup() {
        profile.userModel = user
        user.profileModel = profile

        val profileDaoMock = mock<ProfileService> {}
        val userDaoMock = mock<UserService> {
            on { getByUsername(any()) } doReturn user
        }

        val jwtUtilsMock = mock<JwtUtils> {
            on { isLoggedIn(any()) } doReturn true
            on { loggedInUser(Mockito.any(HttpHeaders::class.java)) } doReturn user
        }

        profileService = ProfileResource(
            profileDaoMock,
            userDaoMock,
            jwtUtilsMock
        )

        Mockito.doNothing().`when`(profileDaoMock).update(any())
    }

    fun testPut() {

        assertEquals("", profile.bio)

        profileService.put("", mock())

        assertEquals("hello world", profile.bio)
    }
}
