package domain

import java.sql.Timestamp
import javax.persistence.*

@Entity
data class Profile(
    @Id
    @GeneratedValue
    var id: Int,
    var screenname: String,
    var created: Timestamp,
    @ManyToMany
    @JoinTable(
        name="liked_kweets",
        joinColumns = [(JoinColumn(name = "profile_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))]
    )
    var likes: List<Kweet>,
    @ManyToMany
    @JoinTable(
        name="follows",
        joinColumns = [(JoinColumn(name="follower_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name="followed_id", referencedColumnName = "id"))]
    )
    var follows: List<Profile>,
    @ManyToMany(mappedBy = "follows")
    var followers: List<Profile>
)
