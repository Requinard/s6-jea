package models

import org.junit.Test
import kotlin.test.assertNotNull

class HashtagModelTest : BaseDomainTest() {

    @Test
    fun persist() {
        val hashtag = HashtagModel(null, "swag")

        tx.begin()
        em.persist(hashtag)
        tx.commit()

        assertNotNull(hashtag.id)
    }
}
