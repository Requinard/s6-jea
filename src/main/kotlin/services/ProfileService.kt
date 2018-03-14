package services

import bridges.ProfileBridge
import models.ProfileModel
import javax.inject.Inject

class ProfileService @Inject constructor(
    val profileBridge: ProfileBridge
) {
    fun create(profileModel: ProfileModel) = profileBridge.create(profileModel)

    fun update(profileModel: ProfileModel) = profileBridge.merge(profileModel)

    fun getAll() = profileBridge.getAll()

    fun getByScreenname(name: String) = profileBridge.getByScreenname(name)

    fun follow(follower: ProfileModel, leader: ProfileModel) = profileBridge.follow(follower, leader)
}
