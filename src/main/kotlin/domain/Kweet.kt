package domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Kweet(
        @Id
        @GeneratedValue()
        val Id: Int?,
        val message: String
)