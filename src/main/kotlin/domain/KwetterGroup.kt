package domain

import javax.persistence.*

@Entity
data class KwetterGroup (
    @Id
    var groupName: String,
    @ManyToMany
    @JoinTable(
        name= "user_group",
        joinColumns = [(JoinColumn(name ="groupName", referencedColumnName = "groupName"))],
        inverseJoinColumns = [(JoinColumn(name="userName", referencedColumnName = "username"))]
    )
    var users: List<KwetterUser> = emptyList()
)
