package services

import bridges.UserBridge
import models.GroupModel
import models.ProfileModel
import models.UserModel
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class UserService @Inject constructor(
    val userBridge: UserBridge
) {
    fun getByUsername(username: String) = userBridge.getUser(username)

    // todo move logic here
    fun createUser(userModel: UserModel, profileModel: ProfileModel) = try {
        userBridge.createUser(userModel, profileModel)
        true
    } catch (ex: Exception) {
        false
    }

    fun createGroup(groupModel: GroupModel) = userBridge.createGroup(groupModel)

    fun addtoGroup(userModel: UserModel, groupModel: GroupModel) = userBridge.addToGroup(userModel, groupModel)

    fun getGroup(name: String) = userBridge.getGroup(name)

    fun removeFromGroup(userModel: UserModel, groupModel: GroupModel) = userBridge.removeFromGroup(userModel, groupModel)

    fun getAllUsers() = userBridge.getAllUsers()
}
