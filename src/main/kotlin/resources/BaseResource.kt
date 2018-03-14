package resources

import models.UserModel
import services.UserService
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

abstract class BaseResource(private val userService: UserService) {
    @Context
    lateinit var request: UriInfo

    @Context
    lateinit var sc: SecurityContext

    val params by lazy { request.queryParameters }
    fun getParam(value: String) = params.get(value)?.first()

    val user: UserModel get() = userService.getByUsername(username)
    val username: String get() = sc.userPrincipal.name
}
