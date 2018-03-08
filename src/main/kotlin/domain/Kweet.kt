package domain

import java.sql.Timestamp
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
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
    NamedQuery(name = "Kweet.getAll", query = "select k from kweet k"),
    NamedQuery(name = "Kweet.search", query = "select k from kweet k where lower(k.message) LIKE LOWER(:query)")
)
data class Kweet(
    @Id
    @GeneratedValue()
    var Id: Int? = null,
    var created: Timestamp,
    @Column(length = 280)
    var message: String
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    lateinit var profile: Profile

    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    var likedBy: Collection<Profile> = emptyList()

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "kweet_hashtag",
        joinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "hashtag_id", referencedColumnName = "id"))]
    )
    var hashtags: List<Hashtag> = emptyList()

    override fun toString(): String = "Field(id:$Id,message:$message"
}
