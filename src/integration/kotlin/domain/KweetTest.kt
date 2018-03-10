package domain

import org.junit.Test
import util.now
import kotlin.test.assertNotNull

class KweetTest : BaseDomainTest() {
    @Test
    fun createKweet() {
        val kweet = Kweet(
            created = now(),
            message = "hello world"
        )

        tx.begin()
        em.persist(kweet)
        tx.commit()

        assertNotNull(kweet.Id)
    }
}
