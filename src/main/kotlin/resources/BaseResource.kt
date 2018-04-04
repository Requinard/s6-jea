package resources

import annotations.Open
import models.UserModel
import services.UserService
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

@Open
abstract class BaseResource(private val userService: UserService) {
    @Context
    private lateinit var request: UriInfo

    @Context
    private lateinit var sc: SecurityContext

    private val params by lazy { request.queryParameters }
    internal fun getParam(value: String) = params[value]?.first()

    val user: UserModel get() = userService.getByUsername(username)
    val username: String get() = sc.userPrincipal.name
}
