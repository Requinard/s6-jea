package dao

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import domain.Hashtag
import domain.Kweet
import domain.Profile
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import util.now
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

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
    val profile = Profile(null, "john", now())

    @Before
    fun setup() {
        val mockQueryFilter = mock<TypedQuery<Kweet>> {
            on { resultList } doReturn kweets.filter { it.message.contains("1") }
        }

        val mockHashtagQueryFilter2 = mock<TypedQuery<Hashtag?>> {
            on { singleResult } doReturn null as Hashtag?
        }

        val mockHashtagQueryFilter = mock<TypedQuery<Hashtag>> {
            on { setParameter(any() as String?, any() as String?) } doReturn mockHashtagQueryFilter2
        }

        val mockQuery = mock<TypedQuery<Kweet>> {
            on { resultList } doReturn kweets
            on { setParameter(any<String>(), any<String>()) } doReturn mockQueryFilter
        }
        val mock = mock<EntityManager> {
            on { createNamedQuery("Kweet.getAll", Kweet::class.java) } doReturn mockQuery
            on { createNamedQuery("Kweet.search", Kweet::class.java) } doReturn mockQuery
            on { createNamedQuery("Hashtag.find", Hashtag::class.java) } doReturn mockHashtagQueryFilter
            on { find(Kweet::class.java, 1) } doReturn kweets[0]
            on { find(Profile::class.java, 1) } doReturn profile
        }

        Mockito.doNothing().`when`(mock).persist(any())

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

    @Test
    fun create() {
        val kweet = Kweet(
            null,
            now(),
            "Hello world"
        )

        kweetDao.create(kweet, profile)

        assertNotNull(kweet.profile)
        assertTrue { profile.kweets.isNotEmpty() }
    }

    @Test
    fun likeByProfile() {
        val kweet = Kweet(
            null,
            now(),
            "Hello world"
        )
        val profile = Profile(
            null,
            "john",
            now()
        )

        // Test to start empty

        assertTrue { kweet.likedBy.isEmpty() }
        assertTrue { profile.likes.isEmpty() }

        kweetDao.create(kweet, profile)
        val success = kweetDao.like(kweet, profile)

        // Verify we liked
        assertTrue { success }
        assertTrue(kweet.likedBy.isNotEmpty())
        assertTrue { profile.likes.isNotEmpty() }

        // Test that  we  cannot refollow
        val fail = kweetDao.like(kweet, profile)

        assertFalse { fail }
    }

    @Test
    fun likeById() {
        val kweet = Kweet(
            null,
            now(),
            "Hello world"
        )

        assertTrue { kweet.likedBy.isEmpty() }
        assertTrue { profile.likes.isEmpty() }

        kweetDao.create(kweet, profile)
        kweetDao.like(kweet, 1)

        assertTrue(kweet.likedBy.isNotEmpty())
        assertTrue { profile.likes.isNotEmpty() }
    }

    @Test
    fun testGetHashTags() {
        val kweet = Kweet(
            created = now(),
            message = "Hello #world. This #is amazing"
        )

        val tags = kweetDao.createHashtags(kweet)

        assertTrue { tags.count() == 2 }
    }
}
