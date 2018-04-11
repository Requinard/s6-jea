package resources

import annotations.Open
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces

@Path("/helloworld")
@Open
class HelloWorldResource {
    @GET
    @Produces("text/plain")
    fun get(): String {
        return "Hello World"
    }
}
