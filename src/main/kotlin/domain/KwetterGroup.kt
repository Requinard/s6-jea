package domain

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
        (NamedQuery(name = "Kwettergroup.find", query = "select g from group g where g.groupname LIKE {:name}"))
    ]
)
data class KwetterGroup(
    @Id
    var groupname: String
) {
    @ManyToMany
    @JoinTable(
        name = "user_group",
        joinColumns = [(JoinColumn(name = "groupname", referencedColumnName = "groupname"))],
        inverseJoinColumns = [(JoinColumn(name = "username", referencedColumnName = "username"))]
    )
    var users = mutableSetOf<KwetterUser>()
}
