package domain

import javax.persistence.* // ktlint-disable no-wildcard-imports

@Entity(name = "kwetteruser")
@NamedQueries(
    NamedQuery(name = "User.getByUsername", query = "select u from kwetteruser u where u.username LIKE :username")
)
data class KwetterUser(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var username: String,
    var password: String,
    @ManyToMany(mappedBy = "users")
    var groups: MutableSet<KwetterGroup> = mutableSetOf()
) {
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = true)
    var profile: Profile? = null
}
