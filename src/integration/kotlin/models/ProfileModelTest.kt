package models

import org.junit.Test
import util.now
import kotlin.test.assertNotNull

class ProfileModelTest : BaseDomainTest() {
    @Test
    fun createProfile() {
        val profile = ProfileModel(
            created = now(),
            screenname = "john"
        )

        tx.begin()
        em.persist(profile)
        tx.commit()

        assertNotNull(profile.id)
    }
}
