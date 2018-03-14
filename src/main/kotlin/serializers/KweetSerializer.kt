package serializers

import domain.Kweet
import util.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class KweetSerializer(private val kweet: Kweet) : Serializable {
    var message: String
        get() = kweet.message
        set(value) = Unit

    var profile: SimpleProfileSerializer
        get() = SimpleProfileSerializer(kweet.profile)
        set(value) = Unit

    var created: Timestamp
        get() = kweet.created
        set(value) = Unit

    var likes: List<SimpleProfileSerializer>
        get() = kweet.likedBy.map { SimpleProfileSerializer(it) }
        set(value) = Unit
}
