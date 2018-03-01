package dao

import domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class ProfileDao {
    @PersistenceContext
    lateinit var em: EntityManager

    fun getById(id: Int) = em.find(Profile::class.java, id)

    fun getByScreenname(name: String) = em.createNamedQuery("Profile.getByScreenName")
        .setParameter("screenname", name)
        .resultList
        .firstOrNull()

    fun create(profile: Profile) = em.persist(profile)

    fun follow(follower: Profile, leader:Profile) {
        follower.follows += leader
        leader.followers += follower
        em.persist(follower)
        em.persist(leader)
    }
}
