package domain

import java.sql.Timestamp
import javax.persistence.*

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

    override fun toString(): String = "Field(id:$Id,message:$message"
}
