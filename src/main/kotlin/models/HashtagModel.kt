package models

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

@Entity(name = "hashtag")
@NamedQueries(
    value = [
        (NamedQuery(name = "HashtagModel.find", query = "select h from hashtag h where h.hashtag LIKE :tag"))
    ]
)
data class HashtagModel(
    @Id
    @GeneratedValue
    var id: Long? = null,
    var hashtag: String
) {
    @ManyToMany(mappedBy = "hashtags")
    var relevantKweets = mutableSetOf<KweetModel>()
}
