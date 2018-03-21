package controllers

import models.UserModel
import org.primefaces.context.RequestContext
import services.UserService
import utils.extensions.logger
import java.io.Serializable
import javax.ejb.Stateless
import javax.inject.Inject
import javax.inject.Named

@Named
@Stateless
class UserController @Inject constructor(
    val userService: UserService
) : Serializable {
    val rc by lazy { RequestContext.getCurrentInstance() }
    fun getAllUsers() = userService.getAllUsers()

    fun makeModerator(user: UserModel) {
        logger.info("Adding ${user.username} to moderator role")
        val group = userService.getGroup("moderators")

        userService.addtoGroup(user, group)
    }

    fun demoteModerator(user: UserModel) {
        logger.info("Removing ${user.username} from moderator role")
        val group = userService.getGroup("moderators")

        userService.removeFromGroup(user, group)

        rc.execute("window.location.reload(true)")
    }
}
