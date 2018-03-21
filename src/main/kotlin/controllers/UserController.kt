package controllers

import services.UserService
import java.io.Serializable
import javax.enterprise.context.SessionScoped
import javax.inject.Inject
import javax.inject.Named

@Named
@SessionScoped
class UserController @Inject constructor(
    val userService: UserService
) : Serializable {
    fun getAllUsers() = userService.getAllUsers()
}
