package dto

import domain.Kweet
import util.Open
import java.sql.Timestamp

@Open
class SimpleKweetFacade(private val kweet: Kweet) {
    var message: String
        get() = kweet.message
        set(value) = Unit

    var profile: Int
        get() = kweet.profile.id!!
        set(value) = Unit

    var created: Timestamp
        get() = kweet.created
        set(value) = Unit
}
