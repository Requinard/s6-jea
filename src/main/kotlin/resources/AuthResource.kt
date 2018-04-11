package resources

import annotations.JwtTokenNeeded
import annotations.Open
import com.google.gson.Gson
import serializers.LoginSerializer
import utils.JwtUtils
import javax.inject.Inject
import javax.ws.rs.Consumes
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Open
@Path("auth")
class AuthResource @Inject constructor(val jwtUtils: JwtUtils) {
    /**
     * Check a token for validity
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JwtTokenNeeded
        fun get() = Response.ok().build()

    /**
     * Log a user in
     */
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    fun login(
        input: String
    ): Response? {
        val loginSerializer = Gson().fromJson(input, LoginSerializer::class.java)

        val token = jwtUtils.login(loginSerializer.username, loginSerializer.password)
            ?: return Response.noContent().build()

        return Response.ok(token).build()
    }

    /**
     * Protected resource
     */
}
