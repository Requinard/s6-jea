package resources

import dao.UserDao
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext
import javax.ws.rs.core.UriInfo

abstract class BaseResource(private val userDao: UserDao) {
    @Context
    lateinit var request: UriInfo

    @Context
    lateinit var sc: SecurityContext

    val params by lazy { request.queryParameters }
    fun getParam(value: String) = params.get(value)?.first()

    val user get() = userDao.getUser(username)
    val username get() = sc.userPrincipal.name
}
