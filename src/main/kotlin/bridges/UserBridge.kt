package bridges

import models.GroupModel
import models.UserModel
import models.ProfileModel
import javax.ejb.Stateless

@Stateless
class UserBridge : BaseBridge() {
    fun getUser(username: String) = em.createNamedQuery("User.getByUsername", UserModel::class.java)
        .setParameter("username", username)
        .singleResult

    fun createUser(userModel: UserModel, profileModel: ProfileModel) {
        em.persist(userModel)
        userModel.profileModel = profileModel
        em.persist(userModel)
        profileModel.userModel = userModel
        em.persist(profileModel)
    }

    fun createGroup(groupModel: GroupModel) = em.persist(groupModel)

    /**
     * Add a userModel to a groupModel
     * @return true if userModel was added, false if userModel already in groupModel
     */
    fun addToGroup(userModel: UserModel, groupModel: GroupModel): Boolean {
        val success = groupModel.users.add(userModel)
        userModel.groups.add(groupModel)
        em.persist(groupModel)
        em.persist(userModel)

        return success
    }

    /**
     * Get a group. If the  group does not exist, create it
     *
     * @return relevant managed group
     */
    fun getGroup(name: String) = em.createNamedQuery("GroupModel.find", GroupModel::class.java)
        .setParameter("name", name)
        .singleResult ?: GroupModel(name).apply { em.persist(this) }

    /**
     * Tries to remove a userModel from a groupModel
     *
     *  @return Whether the  userModel was removed from the groupModel or if it was never in it
     */
    fun removeFromGroup(userModel: UserModel, groupModel: GroupModel): Boolean {
        val success = groupModel.users.remove(userModel)
        userModel.groups.remove(groupModel)
        em.merge(groupModel)
        em.merge(userModel)

        return success
    }

    fun getAllUsers() = em.createNamedQuery("User.getAll", UserModel::class.java).resultList
}