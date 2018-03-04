package domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
data class KwetterUser(
    @Id
    @GeneratedValue
    val id: Long? = null,
    var username: String,
    var password: String,
    @ManyToMany(mappedBy = "users")
    var groups: List<KwetterGroup> = emptyList()
)
