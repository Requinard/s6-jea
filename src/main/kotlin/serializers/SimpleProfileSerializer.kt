package serializers

import models.ProfileModel
import annotations.Open
import java.io.Serializable

@Open
class SimpleProfileSerializer(private val profileModel: ProfileModel) : Serializable {
    var icon: String
        get() = profileModel.profileImage
        set(value) = Unit

    var screenname: String
        get() = profileModel.screenname
        set(value) = Unit
}
