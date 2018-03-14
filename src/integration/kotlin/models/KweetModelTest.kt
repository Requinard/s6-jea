package models

import org.junit.Test
import utils.now
import kotlin.test.assertNotNull

class KweetModelTest : BaseDomainTest() {
    @Test
    fun createKweet() {
        val kweet = KweetModel(
            created = now(),
            message = "hello world"
        )

        tx.begin()
        em.persist(kweet)
        tx.commit()

        assertNotNull(kweet.Id)
    }
}
