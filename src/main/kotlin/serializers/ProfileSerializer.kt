package serializers

import domain.Profile
import util.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class ProfileSerializer(
    private val profile: Profile
) : Serializable {
    var screenname: String
        get() = profile.screenname
        set(value) = Unit

    var kweets: List<SimpleKweetSerializer>
        get() = profile.kweets.take(10)
            .sortedBy { it.created }
            .map { SimpleKweetSerializer(it) }
        set(value) = Unit

    var likedTweets: List<SimpleKweetSerializer>
        get() = profile.likes.map { SimpleKweetSerializer(it) }
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

    var bio: String
        get() = profile.bio
        set(value) = Unit

    var website: String
        get() = profile.website
        set(value) = Unit

    var location: String
        get() = profile.location
        set(value) = Unit
}
