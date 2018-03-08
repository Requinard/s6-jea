package dto

import domain.Kweet
import util.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class KweetFacade(private val kweet: Kweet) : Serializable {
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
class SimpleKweetFacade(private val kweet: Kweet) : Serializable {
    var message: String
        get() = kweet.message
        set(value) = Unit

    var profile: String
        get() = kweet.profile.screenname
        set(value) = Unit

    var created: Timestamp
        get() = kweet.created
        set(value) = Unit
}
