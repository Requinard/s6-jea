package serializers

import domain.Kweet
import util.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class SimpleKweetSerializer(private val kweet: Kweet) : Serializable {
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
