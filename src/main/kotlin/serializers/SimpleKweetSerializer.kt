package serializers

import models.KweetModel
import annotations.Open
import java.io.Serializable
import java.sql.Timestamp

@Open
class SimpleKweetSerializer(private val kweetModel: KweetModel) : Serializable {
    var message: String
        get() = kweetModel.message
        set(value) = Unit

    var profile: String
        get() = kweetModel.profileModel.screenname
        set(value) = Unit

    var created: Timestamp
        get() = kweetModel.created
        set(value) = Unit
}
