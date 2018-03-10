package domain

import org.junit.After
import org.junit.Before
import util.DatabaseCleaner
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence

abstract class BaseDomainTest {
    internal val emf = Persistence.createEntityManagerFactory("integration-local")

    internal lateinit var em: EntityManager
    internal lateinit var tx: EntityTransaction

    @Before
    fun setup() {
        clean()
        em = emf.createEntityManager()
        tx = em.transaction
    }

    fun clean() = DatabaseCleaner(emf.createEntityManager()).clean()

    @After
    fun cleanup() {
        clean()
    }
}
