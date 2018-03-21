package models

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

@Entity(name = "kwettergroup")
@NamedQueries(
    value = [
        (NamedQuery(name = "GroupModel.find", query = "select g from kwettergroup g where g.groupname LIKE :name"))
    ]
)
data class GroupModel(
    @Id
    var groupname: String
) {
    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = [(JoinColumn(name = "groupname", referencedColumnName = "groupname"))],
        inverseJoinColumns = [(JoinColumn(name = "username", referencedColumnName = "username"))]
    )
    var users = mutableSetOf<UserModel>()
}
