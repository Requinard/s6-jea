package serializers.inputs

import java.io.Serializable

data class ChangeProfileSerializer(
    val bio: String?,
    val location: String?,
    val website: String?
) : Serializable
