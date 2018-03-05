package singleton

import dao.KweetDao
import dao.ProfileDao
import dao.UserDao
import domain.Kweet
import domain.KwetterGroup
import domain.KwetterUser
import domain.Profile
import util.now
import util.sha256
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Singleton
@Startup
class DbPopulator @Inject constructor(
    val profileDao: ProfileDao,
    val kweetDao: KweetDao,
    val userDao: UserDao
) {
    val johnUser = KwetterUser(
        username = "steve",
        password = sha256("steve")
    )

    val hankUser = KwetterUser(
        username = "hank",
        password = sha256("hank")
    )

    val group1 = KwetterGroup(
        groupname = "regulars"
    )

    val john = Profile(
        screenname = "john",
        created = now()
    )

    val hank = Profile(
        screenname = "hank",
        created = now()
    )

    val kweet = Kweet(
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
        userDao.createUser(hankUser, hank)
        userDao.addToGroup(hankUser, group1)

        kweetDao.create(kweet, hank)
        kweetDao.like(kweet, john)

        verify()
    }

    /**
     * Grab the relations and test them
     */
    fun verify() {
        val p1 = profileDao.getById(1)
        val p2 = profileDao.getById(2)
    }
}
