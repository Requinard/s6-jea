package models

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToMany
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery
import javax.persistence.OneToOne

@Entity(name = "kwetteruser")
@NamedQueries(
    value = [
        NamedQuery(name = "User.getAll", query = "select u from kwetteruser u"),
        NamedQuery(name = "User.getByUsername", query = "select u from kwetteruser u where u.username LIKE :username")
    ]
)
data class UserModel(
    @GeneratedValue
    val id: Long? = null,
    @Id
    var username: String,
    var password: String
) {
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    var groups = mutableSetOf<GroupModel>()

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id", nullable = true)
    var profileModel: ProfileModel? = null

    fun groupsAsString() = groups.map { it.groupname }.joinToString(",")

    val moderator get() = groupsAsString().contains("moderators")
}
