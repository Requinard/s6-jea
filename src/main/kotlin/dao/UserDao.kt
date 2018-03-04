package dao

import domain.KwetterGroup
import domain.KwetterUser
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class UserDao {
    @PersistenceContext
    lateinit var em: EntityManager

    fun createUser(user: KwetterUser) =
        em.persist(user)

    fun createGroup(group: KwetterGroup) =
        em.persist(group)

    fun addToGroup(user: KwetterUser, group: KwetterGroup) {
        user.groups += group
        group.users += user
        em.persist(group)
        em.persist(user)
    }

}
