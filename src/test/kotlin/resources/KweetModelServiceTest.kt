package resources

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import models.KweetModel
import models.ProfileModel
import models.UserModel
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import services.KweetService
import services.UserService
import utils.JwtUtils
import utils.now
import utils.sha256
import java.sql.Timestamp
import java.time.Instant
import javax.ws.rs.core.HttpHeaders
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

        val kweetDaoMock = mock<KweetService> {
            on { getAllKweets() } doReturn listOf(kweet1)
            on { search("first") } doReturn listOf(kweet2)
        }.apply {
            Mockito.doNothing().`when`(this).create(any(), any())
        }

        val jwtUtilsMock = mock<JwtUtils> {
            on { isLoggedIn(any()) } doReturn true
            on { loggedInUser(Mockito.any(HttpHeaders::class.java)) } doReturn user
        }

        val userDaoMock = mock<UserService> {
            on { getByUsername(any()) } doReturn user
        }
        kweetService = KweetResource(
            kweetDaoMock,
            userDaoMock,
            jwtUtilsMock
        )
    }

    @Test
    fun getAll() {
        val kweets = kweetService.getAll(mock())

        assertEquals(2, kweets.count())
        assertEquals(Timestamp.from(Instant.MAX), kweets.first().created, "Tweet order is wrong!")
    }

    // Test Disable due to errors instantiationg glassfish object
    fun postMessage() {
        val response = kweetService.postMessage("Hello world", mock())

        assertTrue { response.hasEntity() }
    }

    @Test
    fun search() {
        val kweets = kweetService.getByQuery("first")

        assertTrue { kweets.count() == 1 }
    }
}
