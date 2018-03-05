package service

import dao.KweetDao
import dao.UserDao
import domain.Kweet
import domain.KwetterUser
import dto.SimpleKweetFacade
import util.now
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

    private val user: KwetterUser get() = userDao.getUser(sc.userPrincipal.name)!!

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): Collection<SimpleKweetFacade> {
        return user.profile!!.follows.flatMap { it.kweets }
            .sortedByDescending { it.created }
            .map { SimpleKweetFacade(it) }
    }

    @POST
    @Path("{message}")
    @Produces(MediaType.APPLICATION_JSON)
    fun postMessage(
        @PathParam("message") message: String
    ): Response {
        val kweet = Kweet(
            created = now(),
            message = message
        )

        kweetDao.create(kweet, user.profile!!)

        return Response.ok(SimpleKweetFacade(kweet)).build()
    }

    @GET
    @Path("{id}")
    fun getById(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetDao.getById(id) ?: return Response.noContent().build()

        return Response.ok(SimpleKweetFacade(kweet)).build()
    }

    @POST
    @Path("{id}")
    fun likeTweetById(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetDao.getById(id) ?: return Response.noContent().build()

        kweetDao.like(kweet, user.profile!!)

        return Response.ok(kweet).build()
    }
}
