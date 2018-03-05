package service

import dao.KweetDao
import dao.UserDao
import domain.Kweet
import javax.inject.Inject
import javax.ws.rs.* // ktlint-disable no-wildcard-imports
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext

@Path("kweets")
class KweetService @Inject constructor(
    val kweetDao: KweetDao,
    val userDao: UserDao
) {
    @Context
    lateinit var sc: SecurityContext

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): List<Kweet> {
        return kweetDao.getAll()
    }

    @GET
    @Path("{id}")
    fun getById(
        @PathParam("id") id: Int
    ): Kweet {
        return kweetDao.getById(id)
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    fun postKweet(kweet: Kweet): Response {
        var user = userDao.getUser(sc.userPrincipal.name)

        kweetDao.create(kweet, user.profile!!)

        return Response.accepted(kweet).build()
    }
}
