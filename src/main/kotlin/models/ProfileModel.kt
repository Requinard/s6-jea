package models

import java.sql.Timestamp
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
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

@Entity(name = "profileModel")
@NamedQueries(
    (NamedQuery(name = "ProfileModel.getByScreenName", query = "select p from profileModel p where p.screenname LIKE :screenname")),
    (NamedQuery(name = "ProfileModel.getAll", query = "select p from profileModel p"))
)
data class ProfileModel(
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

    @OneToMany(mappedBy = "profileModel", fetch = EAGER)
    var kweets = mutableSetOf<KweetModel>()

    @ManyToMany(fetch = EAGER)
    @JoinTable(
        name = "liked_kweets",
        joinColumns = [(JoinColumn(name = "profile_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "kweet_id", referencedColumnName = "id"))]
    )
    var likes = mutableSetOf<KweetModel>()

    /**
     * A set of profiles that this profileModel follows
     */
    @ManyToMany(fetch = EAGER)
    @JoinTable(
        name = "follows",
        joinColumns = [(JoinColumn(name = "follower_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name = "followed_id", referencedColumnName = "id"))]
    )
    var follows = mutableSetOf<ProfileModel>()

    /**
     * A set of profiles that are following this userModel
     */
    @ManyToMany(mappedBy = "follows", fetch = EAGER)
    var followers = mutableSetOf<ProfileModel>()

    @OneToOne(mappedBy = "profileModel", fetch = EAGER)
    @JoinColumn(name = "user_id", nullable = true)
    var userModel: UserModel? = null
}
