package serializers

import domain.Profile
import util.Open
import java.io.Serializable

@Open
class SimpleProfileSerializer(private val profile: Profile) : Serializable {
    var screenname: String
        get() = profile.screenname
        set(value) = Unit
}
