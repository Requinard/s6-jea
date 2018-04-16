package resources

import annotations.AdminRequired
import annotations.JwtTokenNeeded
import annotations.ModeratorRequired
import annotations.Open
import com.google.gson.Gson
import models.ProfileModel
import models.UserModel
import serializers.ProfileSerializer
import serializers.UserSerializer
import serializers.inputs.RegistrationSerializer
import services.UserService
import utils.now
import utils.sha256
import javax.inject.Inject
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status

@Path("users")
@Open
class UserResource @Inject constructor(
    val userService: UserService
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ModeratorRequired
    @JwtTokenNeeded
    fun get() = userService.getAllUsers().map { UserSerializer(it) }.toList()

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    fun create(
        body: String
    ): Response {
        val reg = Gson().fromJson(body, RegistrationSerializer::class.java)
        val user = UserModel(
            username = reg.username,
            password = sha256(reg.password),
            email = reg.email
        )
        val profile = ProfileModel(
            screenname = reg.username,
            created = now()
        )
        val result = userService.createUser(user, profile)

        return if (result) {
            Response.ok(ProfileSerializer(profile)).build()
        } else {
            Response.status(Status.NOT_ACCEPTABLE).build()
        }
    }
    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    @ModeratorRequired
    @JwtTokenNeeded
    fun getUserById(
        @PathParam("screenname") screenname: String
    ): Response {
        val user = userService.getByUsername(screenname) ?: return Response.status(404, "User not found").build()
        return Response.ok(UserSerializer(user)).build()
    }

    /**
     * Adds a userModel to a group
     * @param screenname screenname to look for
     * @param group group name to look for
     * @return 404 if no userModel, ok if userModel not inputs group, not modified if userModel already inputs group
     */
    @POST
    @Path("{screenname}/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    @AdminRequired
    @JwtTokenNeeded
    fun postUserToGroup(
        @PathParam("screenname") screenname: String,
        @PathParam("group") groupname: String
    ): Response {
        val user = userService.getByUsername(screenname) ?: return Response.status(404, "User not found").build()
        val group = userService.getGroup(groupname)

        val success = userService.addtoGroup(user, group)

        if (success) return Response.ok(UserSerializer(user)).build()
        return Response.notModified("User already inputs group").build()
    }

    @DELETE
    @Path("{screenname}/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    @AdminRequired
    @JwtTokenNeeded
    fun removeUserFromGroup(
        @PathParam("screenname") screenname: String,
        @PathParam("group") groupname: String
    ): Response {
        val user = userService.getByUsername(screenname) ?: return Response.status(404, "User not found").build()
        val group = userService.getGroup(groupname)

        val success = userService.removeFromGroup(user, group)

        if (success) return Response.ok(UserSerializer(user)).build()
        return Response.notModified("User already inputs group").build()
    }
}
