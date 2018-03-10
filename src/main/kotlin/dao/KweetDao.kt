package dao

import domain.Hashtag
import domain.Kweet
import domain.Profile
import javax.ejb.Stateless

@Stateless
class KweetDao : BaseDao() {
    private val hashtagRegex = Regex("""\s(#\w+)""")

    fun getAll(): List<Kweet> = em.createNamedQuery("Kweet.getAll", Kweet::class.java).resultList

    fun getById(id: Int): Kweet? = em.find(Kweet::class.java, id)

    fun create(kweet: Kweet, profile: Profile) {
        kweet.profile = profile
        profile.kweets.add(kweet)
        em.persist(kweet)
        em.merge(profile)

        createHashtags(kweet)
    }

    /**
     * Takes an input kweet and links it to hashtags
     */
    fun createHashtags(kweet: Kweet) = hashtagRegex.findAll(kweet.message).map {
        // Map the matched values to the actual string
        it.value
    }.map {
        // Find a hashtag. If there is no hashtag found we create a new one
        em.createNamedQuery("Hashtag.find", Hashtag::class.java)
            .setParameter("tag", it)
            .singleResult ?: Hashtag(hashtag = it).apply { em.persist(it) }
    }.map {
        // Save the results
        kweet.hashtags.add(it)
        it.relevantKweets.add(kweet)

        em.merge(kweet)
        em.merge(it)

        it
    }

    /**
     * Tries to link a profile to a liked kweet
     * @return true if user has now liked kweet, false if user already liked kweet
     */
    fun like(kweet: Kweet, profile: Profile): Boolean {
        val success = kweet.likedBy.add(profile)
        profile.likes.add(kweet)
        em.persist(kweet)
        em.persist(profile)

        return success
    }

    fun like(kweet: Kweet, profileId: Int): Boolean {
        return like(
            kweet,
            em.find(Profile::class.java, profileId)
        )
    }

    fun search(query: String) = em.createNamedQuery("Kweet.search", Kweet::class.java)
        .setParameter("query", "%$query%")
        .resultList

    fun delete(kweet: Kweet) = em.remove(kweet)
}
