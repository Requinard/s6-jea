package dao

import domain.KwetterGroup
import domain.KwetterUser
import domain.Profile
import javax.ejb.Stateless

@Stateless
class UserDao : BaseDao() {
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

    fun createGroup(group: KwetterGroup) = em.persist(group)

    /**
     * Add a user to a group
     * @return true if user was added, false if user already in group
     */
    fun addToGroup(user: KwetterUser, group: KwetterGroup): Boolean {
        val success = group.users.add(user)
        user.groups.add(group)
        em.persist(group)
        em.persist(user)

        return success
    }

    /**
     * Get a group. If the  group does not exist, create it
     *
     * @return relevant managed group
     */
    fun getGroup(name: String) = em.createNamedQuery("KwetterGroup.find", KwetterGroup::class.java)
        .setParameter("name", name)
        .singleResult ?: KwetterGroup(name).apply { em.persist(this) }

    /**
     * Tries to remove a user from a group
     *
     *  @return Whether the  user was removed from the group or if it was never in it
     */
    fun removeFromGroup(user: KwetterUser, group: KwetterGroup): Boolean {
        val success = group.users.remove(user)
        user.groups.remove(group)
        em.merge(group)
        em.merge(user)

        return success
    }
}
