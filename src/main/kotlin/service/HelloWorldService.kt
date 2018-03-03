package service

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("helloworld")
class HelloWorldService {
    @GET
    @Produces("text/plain")
    fun get(): String {
        return "Hello World"
    }
}
