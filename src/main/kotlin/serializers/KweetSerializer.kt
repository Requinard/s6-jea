package serializers

import models.KweetModel
import annotations.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class KweetSerializer(private val kweetModel: KweetModel) : Serializable {
    var id: Int
        get() = kweetModel.Id!!
        set(value) = Unit

    var message: String
        get() = kweetModel.message
        set(value) = Unit

    var profile: SimpleProfileSerializer
        get() = SimpleProfileSerializer(kweetModel.profileModel)
        set(value) = Unit

    var created: Timestamp
        get() = kweetModel.created
        set(value) = Unit

    var likes: List<SimpleProfileSerializer>
        get() = kweetModel.likedBy.map { SimpleProfileSerializer(it) }
        set(value) = Unit
}
