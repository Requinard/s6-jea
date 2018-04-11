package models

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

@Entity(name = "kweet")
@NamedQueries(
    NamedQuery(name = "KweetModel.getAll", query = "select k from kweet k"),
    NamedQuery(name = "KweetModel.search", query = "select k from kweet k where lower(k.message) LIKE LOWER(:query)")
)
data class KweetModel(
    @Id
    @GeneratedValue()
    var Id: Int? = null,
    var created: Timestamp,
    @Column(length = 280)
    var message: String
) {
    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "profile_id")
    lateinit var profileModel: ProfileModel

    @ManyToMany(mappedBy = "likes", fetch = EAGER)
    var likedBy = mutableSetOf<ProfileModel>()

    @ManyToMany(fetch = EAGER)
    @JoinTable(
        name = "kweet_hashtag",
        joinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "hashtag_id", referencedColumnName = "id"))]
    )
    var hashtags = mutableSetOf<HashtagModel>()

    override fun toString(): String = "Field(id:$Id,message:$message"
}
