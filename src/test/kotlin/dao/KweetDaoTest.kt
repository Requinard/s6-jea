package dao

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import domain.Kweet
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import kotlin.test.assertEquals

internal class KweetDaoTest {
    lateinit var kweetDao: KweetDao

    fun setup() {
        val mockQuery = mock<TypedQuery<*>>() {
            on { resultList } doReturn emptyList<Kweet>()
        }

        val mock = mock<EntityManager> {
            on { createNamedQuery(any()) } doReturn mockQuery
        }

        kweetDao = KweetDao()
        kweetDao.em = mock
    }

    fun getAll() {
        val kweets = kweetDao.getAll()

        assertEquals(0, kweets.count())
    }
}
