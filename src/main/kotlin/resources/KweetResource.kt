package resources

import annotations.JwtTokenNeeded
import annotations.Open
import com.google.gson.Gson
import models.KweetModel
import serializers.KweetSerializer
import serializers.MessageSerializer
import serializers.SimpleKweetSerializer
import services.KweetService
import services.UserService
import utils.JwtUtils
import utils.now
import javax.annotation.security.RolesAllowed
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("kweets")
@Open
class KweetResource @Inject constructor(
    val kweetService: KweetService,
    val userService: UserService,
    val jwtutils: JwtUtils
) {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
    fun getAll(
        @Context header: HttpHeaders
    ): List<KweetSerializer> {
        return jwtutils.loggedInUser(header).profileModel!!.follows.flatMap { it.kweets }
            .sortedBy { it.created }
            .map { KweetSerializer(it) }
    }

    @POST
    @Path("create")
    @JwtTokenNeeded
    @Produces(MediaType.APPLICATION_JSON)
    fun postMessage(
        message: String,
        @Context headers: HttpHeaders
    ): Response {
        val messageSerializer = Gson().fromJson(message, MessageSerializer::class.java)
        val user = jwtutils.loggedInUser(headers)

        val kweet = KweetModel(
            created = now(),
            message = messageSerializer.message
        )

        kweetService.create(kweet, user.profileModel!!)

        return Response.ok(KweetSerializer(kweet)).build()
    }

    @GET
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
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
        @PathParam("id") id: Int,
        @Context headers: HttpHeaders
    ): Response {
        val user = jwtutils.loggedInUser(headers)
        val kweet = kweetService.getKweetById(id) ?: return Response
            .status(404)
            .build()

        kweetService.likeKweet(kweet, user.profileModel!!)

        return Response.ok(SimpleKweetSerializer(kweet)).build()
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
