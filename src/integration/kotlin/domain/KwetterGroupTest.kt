package domain

import org.junit.Test

class KwetterGroupTest : BaseDomainTest() {
    @Test
    fun createGroup() {
        val group = KwetterGroup(
            "regular"
        )

        tx.begin()
        em.persist(group)
        tx.commit()
    }
}
