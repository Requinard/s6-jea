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
import javax.persistence.Lob
import javax.persistence.ManyToMany
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToMany
import javax.persistence.OneToOne

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
    var kweets: Set<Kweet> = emptySet()

    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JoinTable(
        name = "liked_kweets",
        joinColumns = [(JoinColumn(name = "profile_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))]
    )
    var likes: Set<Kweet> = emptySet()

    /**
     * A set of profiles that this profile follows
     */
    @ManyToMany(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JoinTable(
        name = "follows",
        joinColumns = [(JoinColumn(name = "follower_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "followed_id", referencedColumnName = "id"))]
    )
    var follows: MutableSet<Profile> = mutableSetOf()

    /**
     * A set of profiles that are following this user
     */
    @ManyToMany(mappedBy = "follows", fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    var followers: MutableSet<Profile> = mutableSetOf()

    @OneToOne(mappedBy = "profile")
    @JoinColumn(name = "user_id", nullable = true)
    var user: KwetterUser? = null
}
