import annotations.JwtTokenNeeded
import utils.extensions.logger
import utils.isValidJwt
import javax.annotation.Priority
import javax.ws.rs.Priorities
import javax.ws.rs.container.ContainerRequestContext
import javax.ws.rs.container.ContainerRequestFilter
import javax.ws.rs.core.HttpHeaders
import javax.ws.rs.core.Response
import javax.ws.rs.core.Response.Status
import javax.ws.rs.ext.Provider

@Provider
@JwtTokenNeeded
@Priority(Priorities.AUTHENTICATION)
class JwtTokenFilter : ContainerRequestFilter {
    override fun filter(requestContext: ContainerRequestContext) {
        logger.info("Validating JWT token")
        val authHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION)
        val token = authHeader.substring("Bearer".length).trim()

        if (isValidJwt(token)) {
            logger.info("Validation succeeded")
        } else {
            logger.warn("Validation for token failed")
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build())
        }
    }
}
