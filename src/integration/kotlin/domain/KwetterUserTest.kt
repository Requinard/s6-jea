package domain

import org.junit.Test
import kotlin.test.assertNotNull

class KwetterUserTest : BaseDomainTest() {
    @Test
    fun createUser() {
        val user = KwetterUser(
            username = "john",
            password = "test"
        )

        tx.begin()
        em.persist(user)
        tx.commit()

        assertNotNull(user.id)
    }
}
