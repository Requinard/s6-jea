package domain

import org.junit.Test
import util.now
import kotlin.test.assertNotNull

class ProfileTest : BaseDomainTest() {
    @Test
    fun createProfile() {
        val profile = Profile(
            created = now(),
            screenname = "john"
        )

        tx.begin()
        em.persist(profile)
        tx.commit()

        assertNotNull(profile.id)
    }
}
