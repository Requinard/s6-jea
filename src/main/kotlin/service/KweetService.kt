package service

import dao.KweetDao
import domain.Kweet
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces


@Path("kweets")
class KweetService {
    @Inject
    lateinit var kweetDao: KweetDao

    @GET
    @Produces("text/plain")
    fun hi(): String = "Hello world"

    @GET
    @Path("what")
    @Produces("text/plain")
    fun makeKweet(): String {
        kweetDao.create(Kweet(null, "hello world"))
        return "hello"
    }
}