import resources.HelloWorldResource
import resources.KweetResource
import resources.ProfileResource
import resources.UserResource
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/")
class RestApplication : Application() {
    override fun getClasses(): MutableSet<Class<*>> =
        HashSet<Class<*>>().apply {
            add(HelloWorldResource::class.java)
            add(KweetResource::class.java)
            add(ProfileResource::class.java)
            add(UserResource::class.java)
        }
}
