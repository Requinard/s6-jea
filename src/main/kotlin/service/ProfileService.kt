package service

import dao.ProfileDao
import dao.UserDao
import domain.Profile
import dto.ProfileFacade
import javax.inject.Inject
import javax.ws.rs.* // ktlint-disable no-wildcard-imports
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("profiles")
class ProfileService @Inject constructor(
    val profileDao: ProfileDao
) {
    @Inject
    lateinit var userDao: UserDao

    @Context
    lateinit var sc: SecurityContext

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get() = ProfileFacade(profileDao.getByScreenname("john")!!)

    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getByScreenName(
        @PathParam("screenname") screenname: String
    ): Response {
        val profile = profileDao.getByScreenname(screenname)

        if (profile != null)
            return Response.ok(ProfileFacade(profile)).build()
        else return Response.noContent().build()
    }

    @GET
    @Path("{screenname}/kweets")
    @Produces(MediaType.APPLICATION_JSON)
    fun getKweetsByScreenName(
        @PathParam("screenname") screenname: String
    ) = profileDao.getByScreenname(screenname)?.kweets

    @POST
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    fun postFollowScreenname(
        @PathParam("screenname") screenname: String
    ): Response {
        val follower = userDao.getUser(sc.userPrincipal.name).profile

        val leader: Profile = profileDao.getByScreenname(screenname) ?: return Response.status(404, "Did not find leader").build()

        if (follower == null) return Response.noContent().build()
        if (follower.follows.contains(leader)) return Response.notModified("You already follow this user!").build()

        profileDao.follow(follower, leader)

        return Response.ok(ProfileFacade(follower))
            .build()
    }
}
