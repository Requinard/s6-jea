import service.HelloWorldService
import javax.ws.rs.ApplicationPath
import javax.ws.rs.core.Application

@ApplicationPath("/")
class RestApplication: Application() {
    override fun getClasses(): MutableSet<Class<*>> {
        val hashset = HashSet<Class<*>>()

        hashset.add(HelloWorldService::class.java)

        return hashset
    }
}