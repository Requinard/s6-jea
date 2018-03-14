package resources

import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("helloworld")
class HelloWorldResource {
    @GET
    @Produces("text/plain")
    fun get(): String {
        return "Hello World"
    }
}
