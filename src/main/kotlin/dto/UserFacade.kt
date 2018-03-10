package dto

import domain.KwetterUser

class UserFacade(private val user: KwetterUser) {
    var profile: ProfileFacade
        get() = ProfileFacade(user.profile!!)
        set(value) = Unit

    var groups: List<String>
        get() = user.groups.map { it.groupname }
        set(value) = Unit
}
