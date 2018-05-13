package serializers.links

import annotations.Open
import models.KweetModel
import utils.Methods.DELETE
import utils.Methods.GET
import utils.Methods.POST
import utils.link
import java.io.Serializable

@Open
class KweetLinks(private val kweetModel: KweetModel) : Serializable {
    var create = link(POST, "/api/kweets/create", body = mapOf(
        "message" to "string"
    ))
    var timeline = link(GET, "/api/kweets/")
    var details = link(GET, "/api/kweets/${kweetModel.Id}")
    var like = link(POST, "/api/kweets/${kweetModel.Id}")
    var search = link(GET, "/api/kweets/search/{query}", mapOf(
        "query" to "string"
    ))
    var delete = link(DELETE, "/api/kweets/${kweetModel.Id}")
}
