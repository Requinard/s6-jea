package services

import annotations.Open
import bridges.KweetBridge
import bridges.UserBridge
import models.KweetModel
import models.ProfileModel
import javax.ejb.Stateless
import javax.inject.Inject

@Stateless
@Open
class KweetService @Inject constructor(
    val kweetBridge: KweetBridge,
    val userBridge: UserBridge
) {
    fun getAllKweets() = kweetBridge.getAll()
    /**
     * Retrieves a single tweet by it's ID
     */
    fun getKweetById(id: Int) = kweetBridge.getById(id)

    /**
     * Create a new kweet
     */
    fun create(kweet: KweetModel, profile: ProfileModel) = kweetBridge.create(kweet, profile)

    /**
     * see Kweetbridge.like
     */
    fun likeKweet(kweetModel: KweetModel, profile: ProfileModel) = kweetBridge.like(kweetModel, profile)

    /**
     * Searches through tweets
     */
    fun search(query: String) = kweetBridge.search(query)

    /**
     * Removes a kweet
     */
    fun delete(kweet: KweetModel) = kweetBridge.delete(kweet)
}
