package service

import dao.KweetDao
import domain.Kweet
import javax.inject.Inject
import javax.ws.rs.* // ktlint-disable no-wildcard-imports
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("kweets")
class KweetService @Inject constructor(
    val kweetDao: KweetDao
) {
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
        kweetDao.create(kweet)

        return Response.accepted(kweet).build()
    }
}
