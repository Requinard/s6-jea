package dao

import domain.Profile
import javax.ejb.Stateless

@Stateless
class ProfileDao : BaseDao() {

    fun getById(id: Int) = em.find(Profile::class.java, id)

    fun merge(profile: Profile) = em.merge(profile)

    fun getAll(): List<Profile> = em.createNamedQuery("Profile.getAll", Profile::class.java).resultList

    fun getByScreenname(name: String) = em.createNamedQuery("Profile.getByScreenName", Profile::class.java)
        .setParameter("screenname", name)
        .resultList
        .firstOrNull()

    fun create(profile: Profile) = em.persist(profile)

    /**
     * Bidirectionally follow a profile.
     *
     * @return whether user was already following leader
     */
    fun follow(follower: Profile, leader: Profile): Boolean {
        val operation = follower.follows.add(leader)
        leader.followers.add(follower)
        em.merge(follower)
        em.merge(leader)

        return operation
    }
}
