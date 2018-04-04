package bridges

import models.ProfileModel
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class ProfileBridge {
    @PersistenceContext
    lateinit var em: EntityManager

    fun getById(id: Int) = em.find(ProfileModel::class.java, id)

    fun merge(profileModel: ProfileModel) = em.merge(profileModel)

    fun getAll(): List<ProfileModel> = em.createNamedQuery("ProfileModel.getAll", ProfileModel::class.java).resultList

    fun getByScreenname(name: String) = em.createNamedQuery("ProfileModel.getByScreenName", ProfileModel::class.java)
        .setParameter("screenname", name)
        .resultList
        .firstOrNull()

    fun create(profileModel: ProfileModel) = em.persist(profileModel)
}
