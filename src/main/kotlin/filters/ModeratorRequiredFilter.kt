package filters

import annotations.ModeratorRequired
import annotations.Open
import utils.extensions.logger
import utils.verifyToken
import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.Provider

@Provider
@ModeratorRequired
@Priority(Priorities.AUTHORIZATION)
@Open
open class ModeratorRequiredFilter : ContainerRequestFilter {

    override fun filter(requestContext: ContainerRequestContext) {
        logger.info("Validating JWT token")

        val authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)
            ?: "HAHA JA ER IS HELEMAAL GEEN HEADER"

        val token = authHeader.substring("Bearer".length).trim()
        val decoded = verifyToken(token)
            ?: return requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build())

        if (
            decoded.getClaim("groups")
                .asArray(String::class.java)
                .any {
                    listOf("admins", "moderators")
                        .contains(it)
                }) {
            return
        } else {
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build())
        }
    }
}
