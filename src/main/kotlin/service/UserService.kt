package service

import dao.UserDao
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
class UserService @Inject constructor(
    val userDao: UserDao
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    fun get() = Response.ok().build()

    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getUserById(
        @PathParam("screenname") screenname: String
    ): Response {
        userDao.getUser(screenname) ?: return Response.status(404, "User not found").build()
        // todo: add userFacade
        return Response.ok().build()
    }

    /**
     * Adds a user to a group
     * @param screenname screenname to look for
     * @param group group name to look for
     * @return 404 if no user, ok if user not in group, not modified if user already in group
     */
    @POST
    @Path("{screenname}/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    fun postUserToGroup(
        @PathParam("screenname") screenname: String,
        @PathParam("group") groupname: String
    ): Response {
        val user = userDao.getUser(screenname) ?: return Response.status(404, "User not found").build()
        val group = userDao.getGroup(groupname)

        val success = userDao.addToGroup(user, group)

        if (success) return Response.ok().build()
        return Response.notModified("User already in group").build()
    }

    @DELETE
    @Path("{screenname}/{group}")
    @Produces(MediaType.APPLICATION_JSON)
    fun removeUserFromGroup(
        @PathParam("screenname") screenname: String,
        @PathParam("group") groupname: String
    ): Response {
        val user = userDao.getUser(screenname) ?: return Response.status(404, "User not found").build()
        val group = userDao.getGroup(groupname)

        val success = userDao.removeFromGroup(user, group)

        if (success) return Response.ok().build()
        return Response.notModified("User already in group").build()
    }
}
