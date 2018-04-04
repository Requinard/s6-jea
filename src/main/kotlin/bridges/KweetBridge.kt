package bridges

import models.HashtagModel
import models.KweetModel
import models.ProfileModel
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetBridge {
    @PersistenceContext
    lateinit var em: EntityManager

    private val hashtagRegex = Regex("""\s(#\w+)""")

    fun getAll(): List<KweetModel> = em.createNamedQuery("KweetModel.getAll", KweetModel::class.java).resultList

    fun getById(id: Int): KweetModel? = em.find(KweetModel::class.java, id)

    fun create(kweetModel: KweetModel, profileModel: ProfileModel) {
        kweetModel.profileModel = profileModel
        profileModel.kweets.add(kweetModel)
        em.persist(kweetModel)
        em.merge(profileModel)

        createHashtags(kweetModel)
    }

    /**
     * Takes an input kweetModel and links it to hashtags
     * todo: Move this  to service layer
     */
    fun createHashtags(kweetModel: KweetModel) = hashtagRegex.findAll(kweetModel.message).map {
        // Map the matched values to the actual string
        it.value
    }.map {
        // Find a hashtag. If there is no hashtag found we create a new one
        em.createNamedQuery("HashtagModel.find", HashtagModel::class.java)
            .setParameter("tag", it)
            .singleResult ?: HashtagModel(hashtag = it).apply { em.persist(it) }
    }.map {
        // Save the results
        kweetModel.hashtags.add(it)
        it.relevantKweets.add(kweetModel)

        em.merge(kweetModel)
        em.merge(it)

        it
    }

    /**
     * Tries to link a profileModel to a liked kweetModel
     * @return true if userModel has now liked kweetModel, false if userModel already liked kweetModel
     */
    fun like(kweetModel: KweetModel, profileModel: ProfileModel): Boolean {
        val success = kweetModel.likedBy.add(profileModel)
        profileModel.likes.add(kweetModel)
        em.persist(kweetModel)

        return success
    }

    @Deprecated("Use the version with models instead")
    fun like(kweetModel: KweetModel, profileId: Int): Boolean {
        return like(
            kweetModel,
            em.find(ProfileModel::class.java, profileId)
        )
    }

    fun search(query: String): Collection<KweetModel> = em.createNamedQuery("KweetModel.search", KweetModel::class.java)
        .setParameter("query", "%$query%")
        .resultList

    fun delete(kweetModel: KweetModel) {
        val stock = if (em.contains(kweetModel)) kweetModel else em.merge(kweetModel)
        stock.profileModel.kweets.remove(kweetModel)
        em.merge(stock.profileModel)
    }
}
