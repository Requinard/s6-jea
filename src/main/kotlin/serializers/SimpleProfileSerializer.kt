package serializers

import models.ProfileModel
import util.Open
import java.io.Serializable

@Open
class SimpleProfileSerializer(private val profileModel: ProfileModel) : Serializable {
    var screenname: String
        get() = profileModel.screenname
        set(value) = Unit
}
