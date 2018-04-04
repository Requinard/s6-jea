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
import javax.ws.rs.Consumes
import javax.ws.rs.DELETE
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.client.Entity
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

@Path("kweets")
@Open
class KweetResource @Inject constructor(
    val kweetService: KweetService,
    val userService: UserService
) {
    @Context
    private lateinit var request: UriInfo

    @Context
    private lateinit var sc: SecurityContext

    private val params by lazy { request.queryParameters }
    internal fun getParam(value: String) = params[value]?.first()

    internal fun user() = userService.getByUsername(username)
    private val username: String by lazy { sc.userPrincipal.name }

    private fun success(entity: Any) = Response.ok(Entity.text(entity)).build()

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): List<KweetSerializer> {
        return user().profileModel!!.follows.flatMap { it.kweets }
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

        kweetService.create(kweet, user().profileModel!!)

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
        @PathParam("id") id: Int
    ): Response {
        val kweet = kweetService.getKweetById(id) ?: return Response
            .status(404)
            .build()

        kweetService.likeKweet(kweet, user().profileModel!!)

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
