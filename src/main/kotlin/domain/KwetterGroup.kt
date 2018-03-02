package domain

import javax.persistence.*
import javax.persistence.criteria.Join

@Entity(name = "group")
data class KwetterGroup (
    @Id
    @GeneratedValue
    var id: Int,
    var name: String,
    @ManyToMany
    @JoinTable(
        name="user_group",
        joinColumns = [(JoinColumn(name="group_id", referencedColumnName = "id"))],
        inverseJoinColumns = [(JoinColumn(name="user_id", referencedColumnName = "id"))]
    )
    var users: List<User>
)
