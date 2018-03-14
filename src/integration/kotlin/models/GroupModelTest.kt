package models

import org.junit.Test

class GroupModelTest : BaseDomainTest() {
    @Test
    fun createGroup() {
        val group = GroupModel(
            "regular"
        )

        tx.begin()
        em.persist(group)
        tx.commit()
    }
}
