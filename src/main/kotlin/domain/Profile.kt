package domain

import java.sql.Timestamp
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.*

@Entity(name = "profile")
@NamedQueries(
    (NamedQuery(name = "Profile.getByScreenName", query = "select p from profile p where p.screenname LIKE :screenname"))
)
data class Profile(
    @Id
    @GeneratedValue
    var id: Int,
    var screenname: String,
    var created: Timestamp,
    @OneToMany(mappedBy = "profile")
    var kweets: List<Kweet>
    ,
    @ManyToMany
    @JoinTable(
        name = "liked_kweets",
        joinColumns = [(JoinColumn(name = "profile_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))]
    )
    @JsonbTransient
    var likes: List<Kweet>,
    @ManyToMany
    @JoinTable(
        name = "follows",
        joinColumns = [(JoinColumn(name = "follower_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "followed_id", referencedColumnName = "id"))]
    )
    var follows: List<Profile>,
    @ManyToMany(mappedBy = "follows")
    var followers: List<Profile>
)
