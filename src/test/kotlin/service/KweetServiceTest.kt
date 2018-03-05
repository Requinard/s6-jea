package service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.KweetDao
import dao.UserDao
import domain.Kweet
import domain.Profile
import util.now
import java.nio.file.attribute.UserPrincipal
import javax.ws.rs.core.SecurityContext
import kotlin.test.assertEquals

internal class KweetServiceTest {
    lateinit var kweetService: KweetService

    fun setup() {
        val emMock = mock<KweetDao> {
            on { getAll() } doReturn listOf(Kweet(
                created = now(),
                message = "Hi john"
            ).apply {
                profile = Profile(
                    screenname = "john",
                    created = now()
                )
            })
        }

        val userDaoMock = mock<UserDao> {
        }
        val userPrincipalMock = mock<UserPrincipal> {
            on { name } doReturn "john"
        }

        kweetService = KweetService(
            emMock,
            userDaoMock
        )

        kweetService.sc = mock<SecurityContext> {
            on { userPrincipal } doReturn userPrincipalMock
        }
    }

    fun getAll() {
        var kweets = kweetService.getAll()

        assertEquals(1, kweets.count())
        assertEquals("john", kweets.first().profile.screenname)
    }
}
