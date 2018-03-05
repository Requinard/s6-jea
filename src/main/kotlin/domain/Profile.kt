package domain

import java.sql.Timestamp
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
    var created: Timestamp
) {
    @Column(length = 160)
    var bio: String = ""

    var location: String = "Kwetter!"

    var website: String = "www.kwetter.nl"

    @Lob
    @Column(nullable = true)
    var profileImage: ByteArray? = null

    @OneToMany(mappedBy = "profile", fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    var kweets: Collection<Kweet> = emptyList()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JoinTable(
        name = "liked_kweets",
        joinColumns = [(JoinColumn(name = "profile_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))]
    )
    var likes: Collection<Kweet> = emptyList()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JoinTable(
        name = "follows",
        joinColumns = [(JoinColumn(name = "follower_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "followed_id", referencedColumnName = "id"))]
    )
    var follows: Collection<Profile> = emptyList()

    @ManyToMany(mappedBy = "follows", fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    var followers: Collection<Profile> = emptyList()

    @OneToOne(mappedBy = "profile")
    @JoinColumn(name = "user_id", nullable = true)
    var user: KwetterUser? = null
}
