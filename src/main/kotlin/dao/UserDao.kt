package dao

import domain.KwetterGroup
import domain.KwetterUser
import domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class UserDao {
    @PersistenceContext
    lateinit var em: EntityManager

    fun getUser(username: String) = em.createNamedQuery("User.getByUsername", KwetterUser::class.java)
        .setParameter("username", username)
        .singleResult

    fun createUser(user: KwetterUser, profile: Profile) {
        em.persist(user)
        user.profile = profile
        em.persist(user)
        profile.user = user
        em.persist(profile)
    }

    fun createGroup(group: KwetterGroup) =
        em.persist(group)

    fun addToGroup(user: KwetterUser, group: KwetterGroup) {
        user.groups += group
        group.users += user
        em.persist(group)
        em.persist(user)
    }
}
