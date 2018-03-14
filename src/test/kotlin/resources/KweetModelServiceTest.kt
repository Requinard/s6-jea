package resources

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import bridges.KweetBridge
import bridges.UserBridge
import models.KweetModel
import models.UserModel
import models.ProfileModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import util.now
import util.sha256
import java.nio.file.attribute.UserPrincipal
import java.sql.Timestamp
import java.time.Instant
import javax.ws.rs.core.SecurityContext
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class KweetModelServiceTest {
    lateinit var kweetService: KweetResource

    val kweet1 = KweetModel(
        created = now(),
        message = "Hi john"
    )

    val kweet2 = KweetModel(
        created = Timestamp.from(Instant.MAX),
        message = "first"
    )

    val user = UserModel(
        username = "john",
        password = sha256("john")
    )

    val profile = ProfileModel(
        created = now(),
        screenname = "john"
    )

    @Before
    fun setup() {
        user.profileModel = profile
        kweet1.profileModel = profile

        profile.follows = mutableSetOf(
            ProfileModel(
                created = now(),
                screenname = "hank"
            ).apply { kweets = mutableSetOf(kweet1, kweet2) }
        )

        val kweetDaoMock = mock<KweetBridge> {
            on { getAll() } doReturn listOf(kweet1)
            on { search("first") } doReturn listOf(kweet2)
        }.apply {
            Mockito.doNothing().`when`(this).create(any(), any())
        }

        val userDaoMock = mock<UserBridge> {
            on { getUser(any()) } doReturn user
        }

        val userPrincipalMock = mock<UserPrincipal> {
            on { name } doReturn "john"
        }

        val securityContextMock = mock<SecurityContext> {
            on { userPrincipal } doReturn userPrincipalMock
        }

        kweetService = KweetResource(
            kweetDaoMock,
            userDaoMock
        )
        kweetService.sc = securityContextMock
    }

    @Test
    fun getAll() {
        val kweets = kweetService.getAll()

        assertEquals(2, kweets.count())
        assertEquals(Timestamp.from(Instant.MAX), kweets.first().created, "Tweet order is wrong!")
    }

    // Test Disable due to errors instantiationg glassfish object
    fun postMessage() {
        val response = kweetService.postMessage("Hello world")

        assertTrue { response.hasEntity() }
    }

    @Test
    fun search() {
        val kweets = kweetService.getByQuery("first")

        assertTrue { kweets.count() == 1 }
    }
}