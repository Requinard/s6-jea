package bridges

import models.ProfileModel
import javax.ejb.Stateless

@Stateless
class ProfileBridge : BaseBridge() {

    fun getById(id: Int) = em.find(ProfileModel::class.java, id)

    fun merge(profileModel: ProfileModel) = em.merge(profileModel)

    fun getAll(): List<ProfileModel> = em.createNamedQuery("ProfileModel.getAll", ProfileModel::class.java).resultList

    fun getByScreenname(name: String) = em.createNamedQuery("ProfileModel.getByScreenName", ProfileModel::class.java)
        .setParameter("screenname", name)
        .resultList
        .firstOrNull()

    fun create(profileModel: ProfileModel) = em.persist(profileModel)

    /**
     * Bidirectionally follow a profileModel.
     *
     * @return whether userModel was already following leader
     */
    fun follow(follower: ProfileModel, leader: ProfileModel): Boolean {
        val operation = follower.follows.add(leader)
        leader.followers.add(follower)
        em.merge(follower)
        em.merge(leader)

        return operation
    }
}
