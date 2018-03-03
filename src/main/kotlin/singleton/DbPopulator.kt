package singleton

import dao.KweetDao
import dao.ProfileDao
import domain.Kweet
import domain.Profile
import util.now
import javax.annotation.PostConstruct
import javax.ejb.Singleton
import javax.ejb.Startup
import javax.inject.Inject

@Singleton
@Startup
class DbPopulator @Inject constructor(
    val profileDao: ProfileDao,
    val kweetDao: KweetDao
) {
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
        profile = hank,
        message = "Automated entry message"
    )

    @PostConstruct
    fun startup() {
        val profiles = profileDao.getAll()

        if (profiles.isNotEmpty()) return

        profileDao.create(john)
        profileDao.create(hank)

        profileDao.follow(john, hank)

        kweetDao.create(kweet)
        kweetDao.like(kweet, john)
    }
}
