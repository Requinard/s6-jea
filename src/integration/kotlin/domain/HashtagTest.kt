package domain

import org.junit.Test
import kotlin.test.assertNotNull

class HashtagTest : BaseDomainTest() {

    @Test
    fun persist() {
        val hashtag = Hashtag(null, "swag")

        tx.begin()
        em.persist(hashtag)
        tx.commit()

        assertNotNull(hashtag.id)
    }
}
