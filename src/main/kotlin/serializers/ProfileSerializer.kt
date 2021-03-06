package serializers

import annotations.Open
import models.ProfileModel
import serializers.links.ProfileLinks
import java.io.Serializable
import java.sql.Timestamp

@Open
class ProfileSerializer(
    private val profileModel: ProfileModel
) : Serializable {
    var icon: String
        get() = profileModel.profileImage
        set(value) = Unit

    var screenname: String
        get() = profileModel.screenname
        set(value) = Unit

    var kweets: List<KweetSerializer>
        get() = profileModel.kweets.take(10)
            .sortedByDescending { it.created }
            .map { KweetSerializer(it) }
        set(value) = Unit

    var likedTweets: List<SimpleKweetSerializer>
        get() = profileModel.likes.map { SimpleKweetSerializer(it) }
        set(value) = Unit

    var follows: List<SimpleProfileSerializer>
        get() = profileModel.follows.map { SimpleProfileSerializer(it) }
        set(value) = Unit

    var followers: List<SimpleProfileSerializer>
        get() = profileModel.followers.map { SimpleProfileSerializer(it) }
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

    var links = ProfileLinks(profileModel)
}
