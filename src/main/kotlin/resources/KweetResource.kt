package resources

import annotations.Open
import models.KweetModel
import serializers.KweetSerializer
import serializers.SimpleKweetSerializer
import services.KweetService
import services.UserService
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
    val kweetService: KweetService,
    userService: UserService
) : BaseResource(userService) {
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
    fun postMessage(
        @PathParam("message") message: String
    ): Response {
        val kweet = KweetModel(
            created = now(),
            message = message
        )

        kweetService.create(kweet, user.profileModel!!)

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getById(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetService.getKweetById(id) ?: return Response.noContent().build()

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @POST
    @Path("{id}")
    fun likeTweetById(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetService.getKweetById(id) ?: return Response
            .status(404)
            .build()

        kweetService.likeKweet(kweet, user.profileModel!!)

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @GET
    @Path("search/{query}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getByQuery(
        @PathParam("query") query: String
    ) = kweetService.search(query)
        .map { KweetSerializer(it) }
        .toList()

    @DELETE
    @Path("{id}")
    @RolesAllowed("{admins,moderators}")
    fun deleteTweet(
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetService.getKweetById(id) ?: return Response.noContent().build()

        kweetService.delete(kweet)

        return Response.ok(SimpleKweetSerializer(kweet)).build()
    }
}
