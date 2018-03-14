package resources

import bridges.KweetBridge
import bridges.UserBridge
import models.KweetModel
import serializers.KweetSerializer
import serializers.SimpleKweetSerializer
import interceptors.bindings.CensorKweetInterceptorBinding
import annotations.Open
import utils.now
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

@Open
@Path("kweets")
class KweetResource @Inject constructor(
    val kweetDao: KweetBridge,
    userDao: UserBridge
) : BaseResource(userDao) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): List<KweetSerializer> {
        return user.profileModel!!.follows.flatMap { it.kweets }
            .sortedBy { it.created }
            .map { KweetSerializer(it) }
    }

    @POST
    @Path("create/{message}")
    @Produces(MediaType.APPLICATION_JSON)
    @CensorKweetInterceptorBinding
    fun postMessage(
        @PathParam("message") message: String
    ): Response {
        val kweet = KweetModel(
            created = now(),
            message = message
        )

        kweetDao.create(kweet, user.profileModel!!)

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetDao.getById(id) ?: return Response.noContent().build()

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @POST
    @Path("{id}")
    fun likeTweetById(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetDao.getById(id) ?: return Response
            .status(404)
            .build()

        kweetDao.like(kweet, user.profileModel!!)

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @GET
    @Path("search/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getByQuery(
        @PathParam("query") query: String
    ) = kweetDao.search(query)
        .map { KweetSerializer(it) }
        .toList()

    @DELETE
    @Path("{id}")
    @RolesAllowed("{admins,moderators}")
    fun deleteTweet(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetDao.getById(id) ?: return Response.noContent().build()

        kweetDao.delete(kweet)

        return Response.ok(SimpleKweetSerializer(kweet)).build()
    }
}
