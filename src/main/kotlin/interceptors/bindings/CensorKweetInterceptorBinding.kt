package interceptors.bindings

import java.lang.annotation.Inherited
import javax.interceptor.InterceptorBinding

@Inherited
@InterceptorBinding
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class CensorKweetInterceptorBinding
