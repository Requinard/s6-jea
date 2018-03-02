package service

import dao.ProfileDao
import domain.Profile
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("profiles")
class ProfileService @Inject constructor(
    val profileDao: ProfileDao
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Profile? = profileDao.getByScreenname("john")

    @GET
    @Path("{screenname}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getByScreenName(
        @PathParam("screenname") screenname: String
    ) = profileDao.getByScreenname(screenname)

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
        val leader: Profile = profileDao.getByScreenname(screenname) ?: return Response.status(404, "Did not find leader").build()

        profileDao.follow(leader, leader)

        return Response.ok(leader)
            .build()
    }
}
