package domain

import java.sql.Timestamp
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.*

@Entity(name = "profile")
@NamedQueries(
    (NamedQuery(name = "Profile.getByScreenName", query = "select p from profile p where p.screenname LIKE :screenname")),
    (NamedQuery(name = "Profile.getAll", query = "select p from profile p"))
)
data class Profile(
    @Id
    @GeneratedValue
    var id: Int? = null,
    var screenname: String,
    var created: Timestamp,
    @OneToMany(mappedBy = "profile")
    var kweets: List<Kweet> = emptyList()
    ,
    @ManyToMany
    @JoinTable(
        name = "liked_kweets",
        joinColumns = [(JoinColumn(name = "profile_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))]
    )
    @JsonbTransient
    var likes: List<Kweet> = emptyList(),
    @ManyToMany
    @JoinTable(
        name = "follows",
        joinColumns = [(JoinColumn(name = "follower_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "followed_id", referencedColumnName = "id"))]
    )
    var follows: List<Profile> = emptyList(),
    @ManyToMany(mappedBy = "follows")
    var followers: List<Profile> = emptyList()
)
