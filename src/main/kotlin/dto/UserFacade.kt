package dto

import domain.KwetterUser
import util.Open
import java.io.Serializable

@Open
class UserFacade(private val user: KwetterUser) : Serializable {
    var profile: ProfileFacade
        get() = ProfileFacade(user.profile!!)
        set(value) = Unit

    var groups: List<String>
        get() = user.groups.map { it.groupname }
        set(value) = Unit
}
