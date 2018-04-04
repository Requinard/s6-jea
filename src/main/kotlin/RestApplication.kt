import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.server.ResourceConfig
import resources.HelloWorldResource
import resources.KweetResource
import resources.ProfileResource
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/api")
open class RestApplication() : Application() { init {
    ResourceConfig()
        .register(JacksonFeature::class.java)
}

    override fun getClasses(): MutableSet<Class<*>> = mutableSetOf(
        HelloWorldResource::class.java,
        KweetResource::class.java,
        ProfileResource::class.java
    )
}
