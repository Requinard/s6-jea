package bridges

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import domain.KwetterGroup
import domain.KwetterUser
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import util.sha256
import javax.persistence.EntityManager
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserDaoTest {
    lateinit var userDao: UserBridge

    val kwetterUser = KwetterUser(
        username = "john",
        password = sha256("john")
    )

    val kwetterGroup = KwetterGroup(
        groupname = "regular"
    )

    @Before
    fun setup () {
        val mock = mock<EntityManager> {
        }

        Mockito.doNothing().`when`(mock).persist(any())

        userDao = UserBridge().apply {
            em = mock
        }
    }
    @Test
    fun addToGroup() {
        assertTrue { kwetterUser.groups.isEmpty() }

        val success = userDao.addToGroup(kwetterUser, kwetterGroup)

        assertTrue { success }
        assertTrue { kwetterUser.groups.isNotEmpty() }

        val fail = userDao.addToGroup(kwetterUser, kwetterGroup)

        assertFalse { fail }
    }

    @Test
    fun removeFromGroup() {
        kwetterGroup.users.add(kwetterUser)
        kwetterUser.groups.add(kwetterGroup)

        val success = userDao.removeFromGroup(kwetterUser, kwetterGroup)

        assertTrue { success }
        assertTrue { kwetterGroup.users.isEmpty() }

        val fail = userDao.removeFromGroup(kwetterUser, kwetterGroup)

        assertFalse { fail }
    }
}
