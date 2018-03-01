import service.HelloWorldService
import service.KweetService
import service.ProfileService
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/")
class RestApplication : Application() {
    override fun getClasses(): MutableSet<Class<*>> =
        HashSet<Class<*>>().apply {
            add(HelloWorldService::class.java)
            add(KweetService::class.java)
            add(ProfileService::class.java)
        }

}
