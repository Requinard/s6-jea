package annotations

import javax.ws.rs.NameBinding
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.FUNCTION

@NameBinding
@Retention(RUNTIME)
@Target(FUNCTION, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class ModeratorRequired
