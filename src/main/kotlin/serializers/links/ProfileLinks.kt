package serializers.links

import models.ProfileModel
import utils.Methods.GET
import utils.Methods.POST
import utils.Methods.PUT
import utils.link

class ProfileLinks(private val profileModel: ProfileModel) {
    var loggedInUser = link(GET, "/api/profiles")
    var editLoggedInProfile = link(PUT, "/api/profiles", body = mapOf(
        "location" to "string",
        "bio" to "string",
        "website" to "string"
    ))
    var details = link(GET, "/api/profiles/${profileModel.screenname}", mapOf(
        "screenname" to "string"
    ))
    var follow = link(POST, "/api/profiles/${profileModel.screenname}")
}
