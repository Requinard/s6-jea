package models

import org.junit.Test
import kotlin.test.assertNotNull

class UserModelTest : BaseDomainTest() {
    @Test
    fun createUser() {
        val user = UserModel(
            username = "john",
            password = "test"
        )

        tx.begin()
        em.persist(user)
        tx.commit()

        assertNotNull(user.id)
    }
}
