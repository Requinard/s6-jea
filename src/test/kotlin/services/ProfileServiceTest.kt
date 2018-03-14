package services

import bridges.ProfileBridge
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import models.ProfileModel
import org.junit.Before
import org.junit.Test
import utils.now
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProfileServiceTest {
    lateinit var profileService: ProfileService

    val profileNotFollowing = ProfileModel(
        created = now(),
        screenname = "john"
    )

    val profileFollowing = ProfileModel(
        created = now(),
        screenname = "mary"
    )

    val leader = ProfileModel(
        created = now(),
        screenname = "hank"
    )

    @Before
    fun setup() {
        val bridgeMock = mock<ProfileBridge> {
            on { merge(any()) } doReturn profileNotFollowing
        }

        profileService = ProfileService(bridgeMock)
    }

    @Test
    fun follow() {

        leader.followers.add(profileFollowing)
        profileFollowing.follows.add(leader)

        val followSuccesfully = profileService.follow(profileNotFollowing, leader)
        val alreadyFollows = profileService.follow(profileFollowing, leader)

        assertTrue { followSuccesfully }
        assertFalse { alreadyFollows }
    }
}
