package bridges

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import domain.Profile
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import util.now
import javax.persistence.EntityManager
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProfileDaoTest {
    lateinit var profileDao: ProfileBridge

    @Before
    fun setup() {
        val em = mock<EntityManager> {}

        Mockito.doNothing().`when`(em).persist(any())

        profileDao = ProfileBridge()
        profileDao.em = em
    }

    @Test
    fun follow() {
        val profileNotFollowing = Profile(
            created = now(),
            screenname = "john"
        )

        val profileFollowing = Profile(
            created = now(),
            screenname = "mary"
        )

        val leader = Profile(
            created = now(),
            screenname = "hank"
        )

        leader.followers.add(profileFollowing)
        profileFollowing.follows.add(leader)

        val followSuccesfully = profileDao.follow(profileNotFollowing, leader)
        val alreadyFollows = profileDao.follow(profileFollowing, leader)

        assertTrue { followSuccesfully }
        assertFalse { alreadyFollows }
    }
}
