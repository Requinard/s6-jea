package domain

import java.sql.Timestamp
import javax.persistence.*

@Entity(name = "kweet")
@NamedQuery(name = "Kweet.getAll", query = "select k from kweet k")
data class Kweet(
    @Id
    @GeneratedValue()
    var Id: Int? = null,
    var created: Timestamp,
    var message: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    var profile: Profile,
    @ManyToMany(mappedBy = "likes")
    var likedBy: List<Profile> = emptyList()
)
