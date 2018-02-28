package domain

import javax.json.bind.annotation.JsonbTransient
import javax.persistence.*

@Entity
data class Like(
    @Id
    @GeneratedValue
    var id: Int?,
    @ManyToOne()
    @JoinColumn(name = "profile_id")
    @JsonbTransient
    var profile: Profile,
    @ManyToOne()
    @JoinColumn(name = "kweet_id")
    @JsonbTransient
    var kweet: Kweet
)
