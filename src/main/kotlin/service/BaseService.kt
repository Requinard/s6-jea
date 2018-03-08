package service

import dao.UserDao
import javax.ws.rs.core.Context
import javax.ws.rs.core.SecurityContext

abstract class BaseService (private val userDao: UserDao) {
    @Context
    lateinit var sc: SecurityContext

    val user get() = userDao.getUser(username)
    val username get() = sc.userPrincipal.name
}
