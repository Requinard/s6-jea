package serializers

import annotations.Open
import models.KweetModel
import serializers.links.KweetLinks
import java.io.Serializable
import java.sql.Timestamp

@Open
class SimpleKweetSerializer(private val kweetModel: KweetModel) : Serializable {
    var id: Int
        get() = kweetModel.Id!!
        set(value) = Unit
    var message: String
        get() = kweetModel.message
        set(value) = Unit

    var profile: String
        get() = kweetModel.profileModel.screenname
        set(value) = Unit

    var created: Timestamp
        get() = kweetModel.created
        set(value) = Unit

    var links = KweetLinks(kweetModel)
}
