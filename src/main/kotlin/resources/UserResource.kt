package resources

import bridges.UserBridge
import serializers.UserSerializer
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("users")
@RolesAllowed("admin")
class UserResource @Inject constructor(
    val userDao: UserBridge
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("{admins,moderators}")
    fun get() = userDao.getAllUsers().map { UserSerializer(it) }.toList()

    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("{admins,moderators}")
    fun getUserById(
        @PathParam("screenname") screenname: String
    ): Response {
        val user = userDao.getUser(screenname) ?: return Response.status(404, "User not found").build()
        return Response.ok(UserSerializer(user)).build()
    }

    /**
     * Adds a userModel to a group
     * @param screenname screenname to look for
     * @param group group name to look for
     * @return 404 if no userModel, ok if userModel not in group, not modified if userModel already in group
     */
    @POST
    @Path("{screenname}/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("{admins}")
    fun postUserToGroup(
        @PathParam("screenname") screenname: String,
        @PathParam("group") groupname: String
    ): Response {
        val user = userDao.getUser(screenname) ?: return Response.status(404, "User not found").build()
        val group = userDao.getGroup(groupname)

        val success = userDao.addToGroup(user, group)

        if (success) return Response.ok(UserSerializer(user)).build()
        return Response.notModified("User already in group").build()
    }

    @DELETE
    @Path("{screenname}/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("{admins")
    fun removeUserFromGroup(
        @PathParam("screenname") screenname: String,
        @PathParam("group") groupname: String
    ): Response {
        val user = userDao.getUser(screenname) ?: return Response.status(404, "User not found").build()
        val group = userDao.getGroup(groupname)

        val success = userDao.removeFromGroup(user, group)

        if (success) return Response.ok(UserSerializer(user)).build()
        return Response.notModified("User already in group").build()
    }
}
