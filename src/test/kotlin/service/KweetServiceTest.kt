package service

import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import dao.KweetDao
import domain.Kweet
import domain.Profile
import org.junit.Before
import org.junit.Test
import util.now
import kotlin.test.assertEquals

internal class KweetServiceTest {
    lateinit var kweetService: KweetService

    @Before
    fun setup() {
        val emMock = mock<KweetDao> {
            on { getAll() } doReturn listOf(Kweet(
                created = now(),
                message = "Hi john",
                profile = Profile(
                    screenname = "john",
                    created = now()
                )
            ))
        }

        kweetService = KweetService(
            emMock
        )
    }

    @Test
    fun getAll() {
        var kweets = kweetService.getAll()

        assertEquals(1, kweets.count())
        assertEquals("john", kweets.first().profile.screenname)
    }
}
