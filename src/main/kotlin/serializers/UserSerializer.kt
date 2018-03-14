package serializers

import models.UserModel
import util.Open
import java.io.Serializable

@Open
class UserSerializer(private val userModel: UserModel) : Serializable {
    var profile: ProfileSerializer
        get() = ProfileSerializer(userModel.profileModel!!)
        set(value) = Unit

    var groups: List<String>
        get() = userModel.groups.map { it.groupname }
        set(value) = Unit
}
