package services

import bridges.KweetBridge
import bridges.UserBridge
import models.KweetModel
import models.ProfileModel
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
class KweetService @Inject constructor(
    val kweetBridge: KweetBridge,
    val userBridge: UserBridge
) {
    /**
     * Create a new kweet
     */
    fun create(kweet: KweetModel, profile: ProfileModel) = kweetBridge.create(kweet, profile)
}
