package singletons

import bridges.KweetBridge
import bridges.ProfileBridge
import bridges.UserBridge
import models.KweetModel
import models.GroupModel
import models.UserModel
import models.ProfileModel
import utils.now
import utils.sha256
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Singleton
@Startup
class DbPopulatorSingleton @Inject constructor(
    val profileDao: ProfileBridge,
    val kweetDao: KweetBridge,
    val userDao: UserBridge
) {
    val johnUser = UserModel(
        username = "steve",
        password = sha256("steve")
    )

    val hankUser = UserModel(
        username = "hank",
        password = sha256("hank")
    )

    val group1 = GroupModel(
        groupname = "regulars"
    )
    val admins = GroupModel(
        groupname = "admins"
    )
    val mods = GroupModel(
        groupname = "moderators"
    )

    val john = ProfileModel(
        screenname = "john",
        created = now()
    )

    val hank = ProfileModel(
        screenname = "hank",
        created = now()
    )

    val kweet = KweetModel(
        created = now(),
        message = "Automated entry message"
    )

    @PostConstruct
    fun startup() {
        val profiles = profileDao.getAll()

        if (profiles.isNotEmpty()) return

        profileDao.create(john)
        profileDao.create(hank)

        userDao.createGroup(group1)
        userDao.createUser(johnUser, john)
        userDao.addToGroup(johnUser, group1)
        userDao.addToGroup(johnUser, mods)
        userDao.createUser(hankUser, hank)
        userDao.addToGroup(hankUser, group1)

        profileDao.follow(hank, john)

        kweetDao.create(kweet, john)
        kweetDao.like(kweet, hank)
    }
}
