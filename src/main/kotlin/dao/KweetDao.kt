package dao

import domain.Hashtag
import domain.Kweet
import domain.Profile
import javax.ejb.Stateless
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Stateless
class KweetDao {
    @PersistenceContext(unitName = "haraka")
    lateinit var em: EntityManager

    val hashtagRegex = Regex("""\s(#\w+)""")

    fun getAll(): List<Kweet> = em.createNamedQuery("Kweet.getAll", Kweet::class.java).resultList

    fun getById(id: Int): Kweet? = em.find(Kweet::class.java, id)

    fun create(kweet: Kweet, profile: Profile) {
        kweet.profile = profile
        profile.kweets += kweet
        em.persist(kweet)

        createHashtags(kweet)
    }

    fun createHashtags(kweet: Kweet): List<Hashtag> {
        val hashtags = hashtagRegex.findAll(kweet.message)

        return hashtags.map {
            it.value
        }.map {
            em.createNamedQuery("Hashtag.find", Hashtag::class.java)
                .setParameter("tag", it)
                .singleResult ?: Hashtag(hashtag = it).apply { em.persist(it) }
        }.map {
            kweet.hashtags += it
            it.relevantKweets += kweet

            em.merge(kweet)
            em.merge(it)

            it
        }.toList()
    }

    fun like(kweet: Kweet, profile: Profile) {
        kweet.likedBy += profile
        profile.likes += kweet
        em.persist(kweet)
        em.persist(profile)
    }

    fun like(kweet: Kweet, profileId: Int) {
        return like(
            kweet,
            em.find(Profile::class.java, profileId)
        )
    }

    fun search(query: String) = em.createNamedQuery("Kweet.search", Kweet::class.java)
        .setParameter("query", "%$query%")
        .resultList
}
