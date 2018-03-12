package service

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.ProfileDao
import dao.UserDao
import johnProfile
import johnUser
import org.mockito.Mockito
import java.nio.file.attribute.UserPrincipal
import javax.ws.rs.core.MultivaluedHashMap
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo
import kotlin.test.assertEquals

/**
 * Disabled due to responsess
 */
class ProfileServiceTest {
    val profile = johnProfile
    val user = johnUser

    lateinit var profileService: ProfileService

    fun setup() {
        profile.user = user
        user.profile = profile

        val profileDaoMock = mock<ProfileDao> {}
        val userDaoMock = mock<UserDao> {
            on { getUser(any()) } doReturn user
        }

        val scMock = mock<SecurityContext> {
            on { userPrincipal } doReturn com.nhaarman.mockito_kotlin.mock<UserPrincipal> {
                on { name } doReturn "john"
            }
        }

        profileService = ProfileService(
            profileDaoMock,
            userDaoMock
        ).apply {
            sc = scMock
        }

        Mockito.doNothing().`when`(profileDaoMock).merge(any())
    }

    fun testPut() {
        val map = MultivaluedHashMap<String, String>().apply {
            add("bio", "hello world")
        }
        val queryparamsMock = mock<UriInfo> {
            on { getQueryParameters() } doReturn map
        }

        profileService.request = queryparamsMock

        assertEquals("", profile.bio)

        profileService.putByScreenname()

        assertEquals("hello world", profile.bio)
    }
}
