package dao

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import domain.Kweet
import org.junit.Before
import org.junit.Test
import util.now
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import kotlin.test.assertEquals

internal class KweetDaoTest {
    lateinit var kweetDao: KweetDao

    val kweets = listOf(
        Kweet(
            created = now(),
            message = "message 1"
        ),
        Kweet(
            created = now(),
            message = "message 2"
        )
    )

    @Before
    fun setup() {
        val mockQueryFilter = mock<TypedQuery<Kweet>> {
            on { resultList } doReturn kweets.filter { it.message.contains("1") }
        }

        val mockQuery = mock<TypedQuery<Kweet>>() {
            on { resultList } doReturn kweets
            on { setParameter(any<String>(), any<String>())  } doReturn mockQueryFilter
        }

        val mock = mock<EntityManager> {
            on { createNamedQuery("Kweet.getAll", Kweet::class.java) } doReturn mockQuery
            on { createNamedQuery("Kweet.search", Kweet::class.java) } doReturn mockQuery
            on { find(Kweet::class.java, 1) } doReturn kweets[0]
        }

        kweetDao = KweetDao()
        kweetDao.em = mock
    }

    @Test
    fun getAll() {
        val kweets = kweetDao.getAll()

        assertEquals(2, kweets.count())
    }

    @Test
    fun getById() {
        val kweet = kweetDao.getById(1)

        assert(kweet != null)
    }

    @Test
    fun search() {
        val kweets = kweetDao.search("1")

        assertEquals(1, kweets.count())
    }
}
