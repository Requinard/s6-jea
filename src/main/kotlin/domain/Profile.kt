package domain

import java.sql.Timestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Profile(
    @Id
    @GeneratedValue
    var id: Int,
    var screenname: String,
    var created: Timestamp

)
