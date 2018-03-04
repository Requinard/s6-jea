package domain

import com.fasterxml.jackson.annotation.JsonBackReference
import java.sql.Timestamp
import javax.persistence.*

@Entity(name = "kweet")
@NamedQuery(name = "Kweet.getAll", query = "select k from kweet k")
data class Kweet(
    @Id
    @GeneratedValue()
    var Id: Int? = null,
    var created: Timestamp,
    var message: String
) {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    lateinit var profile: Profile

    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    @JsonBackReference
    var likedBy: List<Profile> = emptyList()

    override fun toString(): String = "Field(id:$Id,message:$message"
}
