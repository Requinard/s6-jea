package dao

import domain.Kweet
import domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetDao {
    @PersistenceContext(unitName = "haraka")
    lateinit var em: EntityManager

    fun getAll(): List<Kweet> {
        return em.createNamedQuery("Kweet.getAll", Kweet::class.java).resultList
    }

    fun getById(id: Int): Kweet {
        return em.find(Kweet::class.java, id)
    }

    fun create(kweet: Kweet) {
        em.persist(kweet)
    }

    fun like(kweet: Kweet, profile: Profile) {
        kweet.likedBy += profile
        profile.likes += kweet
        em.persist(kweet)
        em.persist(profile)
    }

    fun like(kweet: Kweet, profileId: Int) {
        return like(
            kweet,
            em.find(Profile::class.java, profileId)
        )
    }
}
