package serializers

import models.ProfileModel
import annotations.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class ProfileSerializer(
    private val profileModel: ProfileModel
) : Serializable {
    var screenname: String
        get() = profileModel.screenname
        set(value) = Unit

    var kweets: List<KweetSerializer>
        get() = profileModel.kweets.take(10)
            .sortedBy { it.created }
            .map { KweetSerializer(it) }
        set(value) = Unit

    var likedTweets: List<SimpleKweetSerializer>
        get() = profileModel.likes.map { SimpleKweetSerializer(it) }
        set(value) = Unit

    var follows: List<String>
        get() = profileModel.follows.map { it.screenname }
        set(value) = Unit

    var created: Timestamp
        get() = profileModel.created
        set(value) = Unit

    var userId: Long?
        get() = profileModel.userModel?.id
        set(value) = Unit

    var bio: String
        get() = profileModel.bio
        set(value) = Unit

    var website: String
        get() = profileModel.website
        set(value) = Unit

    var location: String
        get() = profileModel.location
        set(value) = Unit
}
