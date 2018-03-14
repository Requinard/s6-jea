package serializers

import domain.KwetterUser
import util.Open
import java.io.Serializable

@Open
class UserSerializer(private val user: KwetterUser) : Serializable {
    var profile: ProfileSerializer
        get() = ProfileSerializer(user.profile!!)
        set(value) = Unit

    var groups: List<String>
        get() = user.groups.map { it.groupname }
        set(value) = Unit
}
