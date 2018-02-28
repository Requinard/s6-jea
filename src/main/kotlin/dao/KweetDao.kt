package dao

import domain.Kweet
import javax.ejb.Stateless
import javax.enterprise.context.ApplicationScoped
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetDao() {
    @PersistenceContext(unitName = "haraka")
    lateinit var entityManager: EntityManager

    val it = "hello world"

    fun create(kweet: Kweet) {
        entityManager.persist(kweet)
    }
}