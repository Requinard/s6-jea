package service

import dao.ProfileDao
import domain.Profile
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("profiles")
class ProfileService @Inject constructor(
    val profileDao: ProfileDao
) {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun get(): Profile? = profileDao.getByScreenname("john")
}
