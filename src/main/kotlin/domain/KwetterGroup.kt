package domain

import javax.persistence.*

@Entity
data class KwetterGroup (
    @Id
    var groupname: String,
    @ManyToMany
    @JoinTable(
        name= "user_group",
        joinColumns = [(JoinColumn(name ="groupname", referencedColumnName = "groupname"))],
        inverseJoinColumns = [(JoinColumn(name="username", referencedColumnName = "username"))]
    )
    var users: List<KwetterUser> = emptyList()
)
