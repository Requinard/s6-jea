package domain

import com.fasterxml.jackson.annotation.JsonBackReference
import java.sql.Timestamp
import javax.json.bind.annotation.JsonbTransient
import javax.persistence.* // ktlint-disable no-wildcard-imports

@Entity(name = "kweet")
@NamedQuery(name = "Kweet.getAll", query = "select k from kweet k")
data class Kweet(
    @Id
    @GeneratedValue()
    var Id: Int? = null,
    var created: Timestamp,
    var message: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    var profile: Profile,
    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    @JsonBackReference
    var likedBy: List<Profile> = emptyList()
)
