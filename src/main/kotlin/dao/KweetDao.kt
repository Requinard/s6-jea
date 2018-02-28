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

    fun getAll(): List<Kweet> {
        return entityManager.createNamedQuery("Kweet.getAll").resultList as List<Kweet>
    }

    fun getById(id: Int): Kweet {
        return entityManager.find(Kweet::class.java, id)
    }

    fun create(kweet: Kweet) {
        entityManager.persist(kweet)
    }
}
