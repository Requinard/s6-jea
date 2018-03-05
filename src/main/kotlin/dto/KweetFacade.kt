package dto

import domain.Kweet
import util.Open
import java.sql.Timestamp

@Open
class KweetFacade(private val kweet: Kweet) {
    var message: String
        get() = kweet.message
        set(value) = Unit

    var profile: SimpleProfileFacade
        get() = SimpleProfileFacade(kweet.profile)
        set(value) = Unit

    var created: Timestamp
        get() = kweet.created
        set(value) = Unit

    var likes: List<SimpleProfileFacade>
        get() = kweet.likedBy.map { SimpleProfileFacade(it) }
        set(value) = Unit
}

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
