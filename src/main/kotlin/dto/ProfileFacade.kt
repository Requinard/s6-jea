package dto

import domain.Profile
import util.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class ProfileFacade(
    private val profile: Profile
) : Serializable {
    var screenname: String
        get() = profile.screenname
        set(value) = Unit

    var kweets: List<SimpleKweetFacade>
        get() = profile.kweets.map { SimpleKweetFacade(it) }
        set(value) = Unit

    var likedTweets: List<SimpleKweetFacade>
        get() = profile.likes.map { SimpleKweetFacade(it) }
        set(value) = Unit

    var follows: List<String>
        get() = profile.follows.map { it.screenname }
        set(value) = Unit

    var created: Timestamp
        get() = profile.created
        set(value) = Unit

    var userId: Long?
        get() = profile.user?.id
        set(value) = Unit
}

@Open
class SimpleProfileFacade(private val profile: Profile) : Serializable {
    var screenname: String
        get() = profile.screenname
        set(value) = Unit
}
